package kz.kase.fix.test.func;

import kz.kase.fix.ApplVerID;
import kz.kase.fix.EncryptMethod;
import kz.kase.fix.messages.Logon;
import kz.kase.fix.messages.Logout;
import quickfix.*;

import java.util.concurrent.CountDownLatch;

public class ClientApp implements Application {

    private final CountDownLatch logonLatch = new CountDownLatch(1);

    private final String login, password;

    private SessionID sessionID;
    private Session session;


    public ClientApp(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void onCreate(SessionID sessionID) {
    }

    public void onLogon(SessionID sessionID) {
        this.sessionID = sessionID;
        session = Session.lookupSession(sessionID);
        logonLatch.countDown();
    }

    public void onLogout(SessionID sessionID) {
        this.sessionID = null;
    }

    public void toAdmin(Message message, SessionID sessionID) {
        if (message instanceof Logon) {
            Logon logon = (Logon) message;
            logon.setUsername(login);
            logon.setPassword(password);
            logon.setEncryptMethod(EncryptMethod.NONE_OTHER);
            logon.setResetSeqNum(true);
        }
    }

    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
    }

    public void fromAdmin(Message message, SessionID sessionID)
            throws IncorrectDataFormat,
            IncorrectTagValue, RejectLogon {
        if (message instanceof Logon) {
            Logon logon = (Logon) message;

            System.out.println("Got response:");
            System.out.println(logon);

        } else if (message instanceof Logout) {
            Logout logout = (Logout) message;
            System.out.println("Got exception: " + logout.getText());
        }
    }

    public void fromApp(Message message, SessionID sessionID)
            throws IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {

    }


//    ------------------------------------------------------

    public void sendMessage(Message message) {
        if (session != null /*&& validate(message, session)*/) {
            session.send(message);
        }
    }

    public static boolean validate(Message message, Session session) {
        //todo check dictionary
        DataDictionaryProvider prov = session.getDataDictionaryProvider();
        if (prov != null) {
            try {
                String beginString = session.getSessionID().getBeginString();
                ApplVerID applVerID = MessageUtils.toApplVerID(beginString);
                DataDictionary dict = prov.getApplicationDataDictionary(applVerID);
                dict.validate(message, true);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    public Session getSession() {
        return session;
    }

    public SessionID getSessionID() {
        return sessionID;
    }

    public void awaitLogon() throws InterruptedException {
        logonLatch.await();
    }


}
