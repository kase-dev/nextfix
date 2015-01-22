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

import kz.kase.fix.FixProtocol;
import org.apache.mina.core.session.IoSession;
import quickfix.Message;
import quickfix.MessageUtils;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.logging.Log;
import quickfix.mina.*;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

class AcceptorIoHandler extends AbstractIoHandler {

    private final EventHandlingStrategy eventHandlingStrategy;

    private final AcceptorSessionProvider sessionProvider;

    public AcceptorIoHandler(AcceptorSessionProvider sessionProvider,
                             NetworkingOptions networkingOptions, EventHandlingStrategy eventHandingStrategy) {
        super(networkingOptions);
        this.sessionProvider = sessionProvider;
        eventHandlingStrategy = eventHandingStrategy;
    }

    @Override
        public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        log.info("MINA session created: " + session.getRemoteAddress());
    }

    @Override
    protected void processMessage(IoSession protocolSession, Message message) throws Exception {
        Session qfSession = (Session) protocolSession.getAttribute(SessionConnector.QF_SESSION);
        if (qfSession == null) {
            if (message.getHeader().getString(FixProtocol.FIELD_MESSAGE_TYPE).equals(FixProtocol.MESSAGE_LOGON)) {
                final SessionID sessionID = MessageUtils.getReverseSessionID(message);
                qfSession = sessionProvider.getSession(sessionID, eventHandlingStrategy.getSessionConnector());

                if (qfSession != null) {
                    final Log sessionLog = qfSession.getLog();
                    if (qfSession.hasResponder()) {
                        // Session is already bound to another connection
                        sessionLog.onErrorEvent("Multiple logons/connections for this session are not allowed");
                        protocolSession.close();
                        //todo send Logout response!
                        return;
                    }

                    sessionLog.onEvent("Accepting session " + qfSession.getSessionID() +
                            " from " + protocolSession.getRemoteAddress());

                    final int heartbeatInterval = message.getInt(FixProtocol.FIELD_HEARTBEAT_INT);
                    qfSession.setHeartBeatInterval(heartbeatInterval);
                    sessionLog.onEvent("Acceptor heartbeat set to " + heartbeatInterval + " seconds");

                    protocolSession.setAttribute(SessionConnector.QF_SESSION, qfSession);
                    final NetworkingOptions networkingOptions = getNetworkingOptions();
                    qfSession.setResponder(new IoSessionResponder(protocolSession,
                            networkingOptions.getSynchronousWrites(),
                            networkingOptions.getSynchronousWriteTimeout())
                    );
                } else {
                    log.error("Unknown session ID during logon: " + sessionID);
                    return;
                }
            } else {
                log.warn("Ignoring non-logon message before session establishment: " + message);
                return;
            }
        }

        eventHandlingStrategy.onMessage(qfSession, message);
    }

    @Override
    protected Session findQFSession(IoSession protocolSession, SessionID sessionID) {
        Session s = super.findQFSession(protocolSession, sessionID);
        if (s == null) {
            s = sessionProvider.getSession(sessionID, eventHandlingStrategy.getSessionConnector());
        }
        if (s != null && protocolSession.getAttribute(SessionConnector.QF_SESSION) == null) {
            SocketAddress remoteAddress = protocolSession.getRemoteAddress();
            if (remoteAddress instanceof InetSocketAddress) {
                final InetAddress remoteInetAddress = ((InetSocketAddress) remoteAddress).getAddress();
                if (!s.isAllowedForSession(remoteInetAddress)) {
                    s.getLog().onEvent(
                            "Refused connection to session " + s.getSessionID() + " from "
                                    + remoteAddress);
                    return null;
                }
            }
        }
        return s;
    }

}
