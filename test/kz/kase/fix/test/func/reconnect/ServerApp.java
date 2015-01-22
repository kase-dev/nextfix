package kz.kase.fix.test.func.reconnect;

import kz.kase.fix.messages.Logon;
import quickfix.*;

public class ServerApp extends ApplicationAdapter {

    @Override
    public void fromAdmin(Message message, SessionID sessionID)
            throws IncorrectDataFormat,
            IncorrectTagValue, RejectLogon {

        if (message instanceof Logon) {
            process((Logon) message, sessionID);
        }
    }


    @Override
    public void onLogon(SessionID sessionId) {
        super.onLogon(sessionId);
    }

    @Override
    public void onLogout(SessionID sessionId) {
        super.onLogout(sessionId);
    }

    private void process(Logon request, SessionID sessionId) throws RejectLogon {

        if (request.hasUsername() && request.hasPassword()) {
            String username = request.getUsername();
            String pass = request.getPassword();

            System.out.println("username = " + username);
            System.out.println("pass = " + pass);
        }
    }


}
