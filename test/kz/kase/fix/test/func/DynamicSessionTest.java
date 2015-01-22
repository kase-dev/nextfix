package kz.kase.fix.test.func;

import kz.kase.fix.EncryptMethod;
import kz.kase.fix.ManualSessionProvider;
import kz.kase.fix.core.FixUtils;
import kz.kase.fix.factory.KaseFixMessageFactory;
import kz.kase.fix.messages.Logon;
import org.junit.Test;
import quickfix.*;
import quickfix.logging.LogFactory;
import quickfix.logging.ScreenLogFactory;
import quickfix.mina.acceptor.AbstractSocketAcceptor;
import quickfix.store.FileStoreFactory;
import quickfix.store.MessageStoreFactory;

import java.io.FileNotFoundException;
import java.util.concurrent.CountDownLatch;

public class DynamicSessionTest {

    public static final String USER_DIR = System.getProperty("user.dir");

    private ManualSessionProvider provider;


    private void initServer(String configPath, String... logins) throws FileNotFoundException, ConfigError {
        ServerApp serverApp = new ServerApp();
        SessionSettings settings = FixUtils.getSettings(configPath);
        if (logins != null) {
            for (String login : logins) {
                FixUtils.addAcceptorSession(settings, login);
            }
        }

        MessageFactory mesFactory = new KaseFixMessageFactory();
        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new ScreenLogFactory(true, true, true, false);

        AbstractSocketAcceptor acceptor = new ThreadedSocketAcceptor(serverApp,
                storeFactory, settings, logFactory, mesFactory);
        acceptor.start();

        provider = acceptor.getProvider();
    }

    private void addSession(String username) throws ConfigError {
        provider.addSession(username);
    }


    SocketInitiator clientInitiator;

    private ClientApp initClient(String configPath, String login, String pass)
            throws FileNotFoundException, ConfigError, InterruptedException {

        ClientApp app = new ClientApp(login, pass);
        SessionSettings settings = FixUtils.getSettings(configPath);
        FixUtils.addSession(settings, login);

        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new ScreenLogFactory(true, true, true, false);
        MessageFactory mesFactory = new KaseFixMessageFactory();

        clientInitiator =
                new SocketInitiator(app, storeFactory, settings, logFactory, mesFactory);

        clientInitiator.start();
        return app;
    }

    private void stopClient() {
        clientInitiator.stop();
    }

    @Test
    public void startServerAndClient() throws Exception {
        String serverConfig = USER_DIR + "/server-dyn.cfg";
        String clientConfig = USER_DIR + "/client-dyn.cfg";

        String usernameCorrect = "U001";
        String username = "U234401";
        String pass = "123";
//        String pass = "124";

        initServer(serverConfig/*, username*/);

        Thread.sleep(500);

        addSession(usernameCorrect);


        ClientApp app = initClient(clientConfig, usernameCorrect, pass);
//        initClient(clientConfig, usernameCorrect, pass);


//        Thread.sleep(500);

//        stopClient();

        Thread.sleep(25000);

//        app.awaitLogon();

    }

//    @Test
//    public void startServerAnd2Clients() throws Exception {
//        String serverConfig = USER_DIR + "/server-dyn.cfg";
//        String clientConfig = USER_DIR + "/client-dyn.cfg";
//
//        String username = "U001";
//        String pass = "123";
//
//        String username2 = "U001";
//        String pass2 = "123";
//
//        initServer(serverConfig/*, username*/);
//
//        Thread.sleep(500);
//
//        addSession(username);
//
////        addSession(username2);
//
//
//        initClient(clientConfig, username, pass);
//
//        initClient(clientConfig, username2, pass2);
//
//        Thread.sleep(500);
//    }


    private static class ServerApp implements Application {
        @Override
        public void fromApp(Message message, SessionID sessionId)
                throws IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        }

        @Override
        public void toApp(Message message, SessionID sessionId)
                throws DoNotSend {
        }

        @Override
        public void fromAdmin(Message message, SessionID sessionId)
                throws IncorrectDataFormat, IncorrectTagValue, RejectLogon {
            if (message instanceof Logon) {
                Logon logon = (Logon) message;
                System.out.println("Got logon: " + logon.getUsername() + ":" + logon.getPassword());
                if (!"123".equals(logon.getPassword())) {
                    throw new RejectLogon("Wrong password!");
                } else if (!"U001".equals(logon.getUsername())) {
                    throw new RejectLogon("Wrong user");
                } else {
                    System.out.println("Password is ok");
                }
            }
        }

        @Override
        public void toAdmin(Message message, SessionID sessionId) {
        }

        @Override
        public void onLogout(SessionID sessionId) {
//            Session session = Session.lookupSession(sessionId);
//            System.out.println(session.getSessionId());

        }

        @Override
        public void onLogon(SessionID sessionId) {
//            Session session = Session.lookupSession(sessionId);
//            session.setTradeSessionId(1232131L);
        }

        @Override
        public void onCreate(SessionID sessionId) {
        }
    }


    private static class ClientApp implements Application {

        private final String login;
        private final String password;

        private final CountDownLatch logonLatch = new CountDownLatch(1);

        private ClientApp(String login, String pass) {
            this.login = login;
            this.password = pass;
        }

        @Override
        public void onCreate(SessionID sessionId) {
        }

        @Override
        public void onLogon(SessionID sessionId) {
            System.out.println("DynamicSessionTest$ClientApp.onLogon");
            Session session = Session.lookupSession(sessionId);
            logonLatch.countDown();
        }

        @Override
        public void onLogout(SessionID sessionId) {
            System.out.println("DynamicSessionTest$ClientApp.onLogout");
        }

        @Override
        public void toAdmin(Message message, SessionID sessionId) {
            if (message instanceof Logon) {
                Logon logon = (Logon) message;
                logon.setUsername(login);
                logon.setPassword(password);
                logon.setEncryptMethod(EncryptMethod.NONE_OTHER);
                logon.setResetSeqNum(true);
            }
        }

        @Override
        public void fromAdmin(Message message, SessionID sessionId) throws IncorrectDataFormat, IncorrectTagValue, RejectLogon {
            System.out.println("fromAdmin");
        }

        @Override
        public void toApp(Message message, SessionID sessionId) throws DoNotSend {
        }

        @Override
        public void fromApp(Message message, SessionID sessionId) throws IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
            System.out.println("test");
        }

        //        8=FIX.4.49=7235=534=149=Server52=20140212-17:21:46.65356=U00158=Wrong password!10=162
        public void awaitLogon() throws InterruptedException {
            logonLatch.await();
        }
    }
}
