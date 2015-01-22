package kz.kase.fix;

import kz.kase.fix.core.FixUtils;
import quickfix.*;
import quickfix.mina.SessionConnector;
import quickfix.mina.acceptor.AcceptorSessionProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ManualSessionProvider implements AcceptorSessionProvider {


    private final Map<SessionID, Session> acceptorSessions =
            new ConcurrentHashMap<SessionID, Session>();

    private final SessionSettings settings;
    private final SessionFactory sessionFactory;

    public ManualSessionProvider(SessionSettings settings,
                                 SessionFactory sessionFactory,
                                 Map<SessionID, Session> acceptorSessions) {

        this.settings = settings;
        this.sessionFactory = sessionFactory;
        this.acceptorSessions.putAll(acceptorSessions);
    }

    public void addSession(String username) throws ConfigError {

        SessionID sessionID = FixUtils.addAcceptorSession(settings, username);
        Session session = sessionFactory.create(sessionID, settings);

        acceptorSessions.put(session.getSessionID(), session);
    }


    public Session getSession(SessionID sessionID, SessionConnector ignored) {
        Session session = acceptorSessions.get(sessionID);

        if (session == null) {
            try {
                addSession(sessionID.getTargetCompID());
            } catch (ConfigError configError) {
                configError.printStackTrace();
            }

            session = acceptorSessions.get(sessionID);
        }

        if (session == null) {
            session = acceptorSessions.get(reduceSessionID(sessionID));
        }
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
