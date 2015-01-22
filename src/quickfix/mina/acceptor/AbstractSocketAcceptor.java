/*******************************************************************************
 * Copyright (c) quickfixengine.org  All rights reserved.
 *
 * This file is part of the QuickFIX FIX Engine
 *
 * This file may be distributed under the terms of the quickfixengine.org
 * license as defined by quickfixengine.org and appearing in the file
 * LICENSE included in the packaging of this file.
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING
 * THE WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE.
 *
 * See http://www.quickfixengine.org/LICENSE for licensing information.
 *
 * Contact ask@quickfixengine.org if any conditions of this licensing
 * are not clear to you.
 ******************************************************************************/

package quickfix.mina.acceptor;

import kz.kase.fix.ManualSessionProvider;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import quickfix.*;
import quickfix.logging.LogFactory;
import quickfix.logging.ScreenLogFactory;
import quickfix.mina.CompositeIoFilterChainBuilder;
import quickfix.mina.EventHandlingStrategy;
import quickfix.mina.NetworkingOptions;
import quickfix.mina.SessionConnector;
import quickfix.mina.message.FIXProtocolCodecFactory;
import quickfix.mina.ssl.SSLContextFactory;
import quickfix.mina.ssl.SSLSupport;
import quickfix.store.MessageStoreFactory;

import javax.net.ssl.SSLContext;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static quickfix.mina.message.FIXProtocolCodecFactory.FILTER_NAME;

//import org.apache.mina.common.*;
//import org.apache.mina.filter.SSLFilter;
//import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;

/**
 * Abstract base class for socket acceptors.
 */
public abstract class AbstractSocketAcceptor extends SessionConnector implements Acceptor {

    private final Map<SocketAddress, AcceptorSessionProvider> sessionProviders = new HashMap<>();
    private final SessionFactory sessionFactory;
    private final Map<SocketAddress, AcceptorSocketDescriptor> socketDescriptorForAddress = new HashMap<>();
    private IoAcceptor ioAcceptor;

    protected AbstractSocketAcceptor(SessionSettings settings, SessionFactory sessionFactory)
            throws ConfigError {
        super(settings, sessionFactory);
//        IoBuffer.setAllocator(new SimpleByteBufferAllocator());
//        IoBuffer.setUseDirectBuffers(false);
        this.sessionFactory = sessionFactory;
    }

    protected AbstractSocketAcceptor(Application application,
                                     MessageStoreFactory messageStoreFactory, SessionSettings settings,
                                     MessageFactory messageFactory) throws ConfigError {
        this(application, messageStoreFactory, settings, new ScreenLogFactory(settings),
                messageFactory);
    }

    protected AbstractSocketAcceptor(Application application,
                                     MessageStoreFactory messageStoreFactory, SessionSettings settings,
                                     LogFactory logFactory, MessageFactory messageFactory) throws ConfigError {
        this(settings, new DefaultSessionFactory(application, messageStoreFactory, logFactory,
                messageFactory));
    }

    public ManualSessionProvider getProvider() {
        return (ManualSessionProvider) sessionProviders.values().iterator().next();
    }


    protected /*synchronized*/ void startAcceptingConnections() throws ConfigError {
        try {
            createSessions(getSettings());
            startSessionTimer();
            SessionSettings settings = getSettings();

            for (AcceptorSocketDescriptor descr : socketDescriptorForAddress.values()) {
                IoAcceptor ioAcceptor = getIoAcceptor();

                CompositeIoFilterChainBuilder ioFilterChainBuilder =
                        new CompositeIoFilterChainBuilder(getIoFilterChainBuilder());

                if (descr.isUseSSL()) {
                    installSSL(descr, ioFilterChainBuilder);
                }

                ioFilterChainBuilder.addLast(FILTER_NAME, new ProtocolCodecFilter(new FIXProtocolCodecFactory()));
                ioAcceptor.setFilterChainBuilder(ioFilterChainBuilder);

                AcceptorSessionProvider sessionProvider = sessionProviders.get(descr.getAddress());
                if (sessionProvider == null) {
                    sessionProvider = new ManualSessionProvider(
                            settings, sessionFactory,
                            descr.getAcceptedSessions());
                    sessionProviders.put(descr.getAddress(), sessionProvider);
                }

                ioAcceptor.setHandler(new AcceptorIoHandler(sessionProvider,
                        new NetworkingOptions(settings.getDefaultProperties()),
                        getEventHandlingStrategy()));
                ioAcceptor.bind(descr.getAddress());


                log.info("Listening for connections at " + descr.getAddress());
            }
        } catch (FieldConvertError e) {
            throw new ConfigError(e);
        } catch (Exception e) {
            throw new RuntimeError(e);
        }
    }


    private void installSSL(AcceptorSocketDescriptor descriptor,
                            CompositeIoFilterChainBuilder ioFilterChainBuilder) throws GeneralSecurityException {
        log.info("Installing SSL filter for " + descriptor.getAddress());
        SSLContext sslContext = SSLContextFactory.getInstance(descriptor.getKeyStoreName(),
                descriptor.getKeyStorePassword().toCharArray(), descriptor.getKeyStoreName(),
                descriptor.getKeyStorePassword().toCharArray());
        SslFilter sslFilter = new SslFilter(sslContext);
        sslFilter.setUseClientMode(false);
        ioFilterChainBuilder.addLast(SSLSupport.FILTER_NAME, sslFilter);
    }

    private IoAcceptor getIoAcceptor() {
        if (ioAcceptor == null) {
            ioAcceptor = new NioSocketAcceptor();
        }
        return ioAcceptor;
    }

    private AcceptorSocketDescriptor getAcceptorSocketDescriptor(SessionSettings settings,
                                                                 SessionID sessionID) throws ConfigError, FieldConvertError {

        boolean useSSL = false;
        String keyStoreName = null;
        String keyStorePassword = null;
        if (getSettings().isSetting(sessionID, SSLSupport.SETTING_USE_SSL)
                && getSettings().getBool(sessionID, SSLSupport.SETTING_USE_SSL)) {
            useSSL = true;
            keyStoreName = SSLSupport.getKeystoreName(getSettings(), sessionID);
            keyStorePassword = SSLSupport.getKeystorePasswd(getSettings(), sessionID);
        }

        int acceptPort = (int) settings.getLong(sessionID, Acceptor.SETTING_SOCKET_ACCEPT_PORT);

        String acceptHost = null;
        if (settings.isSetting(sessionID, SETTING_SOCKET_ACCEPT_ADDRESS)) {
            acceptHost = settings.getString(sessionID, SETTING_SOCKET_ACCEPT_ADDRESS);
        }

        SocketAddress acceptorAddress = acceptHost != null ?
                new InetSocketAddress(acceptHost, acceptPort) :
                new InetSocketAddress(acceptPort);

        // Check for cached descriptor
        AcceptorSocketDescriptor descriptor = socketDescriptorForAddress
                .get(acceptorAddress);
        if (descriptor != null) {
            if (descriptor.isUseSSL() && !useSSL
                    || !equals(descriptor.getKeyStoreName(), keyStoreName)
                    || !equals(descriptor.getKeyStorePassword(), keyStorePassword)) {
                throw new ConfigError("Conflicting configurations of acceptor socket: "
                        + acceptorAddress);
            }
        } else {
            descriptor = new AcceptorSocketDescriptor(acceptorAddress, useSSL, keyStoreName,
                    keyStorePassword);
            socketDescriptorForAddress.put(acceptorAddress, descriptor);
        }

        return descriptor;
    }

    private boolean equals(Object object1, Object object2) {
        return object1 == null ? object2 == null : object1.equals(object2);
    }

    private void createSessions(SessionSettings settings) throws ConfigError, FieldConvertError {
        HashMap<SessionID, Session> allSessions = new HashMap<>();
        for (Iterator<SessionID> i = settings.sectionIterator(); i.hasNext(); ) {
            SessionID sessionID = i.next();
            String connectionType = settings.getString(sessionID,
                    SessionFactory.SETTING_CONNECTION_TYPE);

            boolean isTemplate = false;
            if (settings.isSetting(sessionID, Acceptor.SETTING_ACCEPTOR_TEMPLATE)) {
                isTemplate = settings.getBool(sessionID, Acceptor.SETTING_ACCEPTOR_TEMPLATE);
            }

            if (connectionType.equals(SessionFactory.ACCEPTOR_CONNECTION_TYPE)) {
                AcceptorSocketDescriptor descriptor = getAcceptorSocketDescriptor(settings, sessionID);
                if (!isTemplate) {
                    Session session = sessionFactory.create(sessionID, settings);
                    descriptor.acceptSession(session);
                    allSessions.put(sessionID, session);
                }
            }
        }
        setSessions(allSessions);

        if (socketDescriptorForAddress.size() == 0) {
            throw new ConfigError("No acceptor sessions found in settings.");
        }
    }

    protected void stopAcceptingConnections() {
        for (AcceptorSocketDescriptor descr : socketDescriptorForAddress.values()) {
            SocketAddress acceptorSocketAddress = descr.getAddress();
            log.info("No longer accepting connections on " + acceptorSocketAddress);
            IoAcceptor ioAcceptor = getIoAcceptor();
//            if (ioAcceptor.isManaged(acceptorSocketAddress)) {
//                ioAcceptor.unbind(acceptorSocketAddress);
//            }
        }
    }

    private static class AcceptorSocketDescriptor {
        private final SocketAddress address;
        private final boolean useSSL;
        private final String keyStoreName;
        private final String keyStorePassword;
        private final Map<SessionID, Session> acceptedSessions = new HashMap<SessionID, Session>();

        public AcceptorSocketDescriptor(SocketAddress address, boolean useSSL, String keyStoreName,
                                        String keyStorePassword) {
            this.address = address;
            this.useSSL = useSSL;
            this.keyStoreName = keyStoreName;
            this.keyStorePassword = keyStorePassword;
        }

        public void acceptSession(Session session) {
            acceptedSessions.put(session.getSessionID(), session);
        }

        public Map<SessionID, Session> getAcceptedSessions() {
            return Collections.unmodifiableMap(acceptedSessions);
        }

        public SocketAddress getAddress() {
            return address;
        }

        public String getKeyStoreName() {
            return keyStoreName;
        }

        public String getKeyStorePassword() {
            return keyStorePassword;
        }

        public boolean isUseSSL() {
            return useSSL;
        }

    }


    public Map<SessionID, SocketAddress> getAcceptorAddresses() {
        Map<SessionID, SocketAddress> sessionIdToAddressMap = new HashMap<SessionID, SocketAddress>();
        for (AcceptorSocketDescriptor descriptor : socketDescriptorForAddress.values()) {
            for (SessionID sessionID : descriptor.getAcceptedSessions().keySet()) {
                sessionIdToAddressMap.put(sessionID, descriptor.getAddress());
            }
        }
        return sessionIdToAddressMap;
    }

    public void setSessionProvider(SocketAddress address, AcceptorSessionProvider provider) {
        sessionProviders.put(address, provider);
    }

    static class StaticAcceptorSessionProvider implements AcceptorSessionProvider {
        private final Map<SessionID, Session> acceptorSessions;

        public StaticAcceptorSessionProvider(final Map<SessionID, Session> acceptorSessions) {
            this.acceptorSessions = acceptorSessions;
        }

        public Session getSession(SessionID sessionID, SessionConnector connector) {
            return acceptorSessions.get(sessionID);
        }
    }

    public int getQueueSize() {
        final EventHandlingStrategy ehs = getEventHandlingStrategy();
        return ehs == null ? 0 : ehs.getQueueSize();
    }

    protected abstract EventHandlingStrategy getEventHandlingStrategy();

    private class DefaultAcceptorSessionProvider
            implements AcceptorSessionProvider {
        private final Map<SessionID, Session> acceptorSessions;

        public DefaultAcceptorSessionProvider(Map<SessionID, Session> acceptorSessions) {
            this.acceptorSessions = acceptorSessions;
        }

        public Session getSession(SessionID sessionID, SessionConnector ignored) {
            Session session = acceptorSessions.get(sessionID);
            if (session == null)
                session = acceptorSessions.get(reduceSessionID(sessionID));
            return session;
        }

        /**
         * Remove the extra fields added to the session ID in QF-272.
         */
        private SessionID reduceSessionID(SessionID sessionID) {
            // Acceptors don't use qualifiers.
            return new SessionID(sessionID.getBeginString(), sessionID.getSenderCompID(), sessionID.getTargetCompID());
        }
    }


}
