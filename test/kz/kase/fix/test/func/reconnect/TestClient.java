package kz.kase.fix.test.func.reconnect;

import kz.kase.fix.core.FixUtils;
import kz.kase.fix.factory.KaseFixMessageFactory;
import kz.kase.fix.test.func.ClientApp;
import quickfix.*;
import quickfix.logging.LogFactory;
import quickfix.logging.ScreenLogFactory;
import quickfix.store.FileStoreFactory;
import quickfix.store.MessageStoreFactory;

import java.io.FileNotFoundException;

public class TestClient {

    private static long ref = 0;
    private final SocketInitiator initiator;
    private final ClientApp clientApp;

    public TestClient(String configPath, String login, String pass)
            throws ConfigError, FileNotFoundException {

        this.clientApp = new ClientApp(login, pass);
        SessionSettings settings = FixUtils.getSettings(configPath);

        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new ScreenLogFactory(true, true, true, false);
        MessageFactory mesFactory = new KaseFixMessageFactory();

        initiator = new SocketInitiator(clientApp, storeFactory, settings, logFactory, mesFactory);
    }


    public void awaitForLogin() throws InterruptedException {
        clientApp.awaitLogon();
    }


    public static long nextRef() {
        return ++ref;
    }

    public ClientApp getClientApp() {
        return clientApp;
    }

    public void start() throws ConfigError {
        initiator.start();
    }


    public void logout() {
        for (SessionID sessionId : initiator.getSessions()) {
            Session.lookupSession(sessionId).logout("user requested");
        }
    }


    public static void main(String[] args) throws FileNotFoundException, ConfigError, InterruptedException {
        String config = "test-client-config.cfg";
//        String login = "03206";
        String login = "03205";
//        String pass = "WQVYY";
        String pass = "WQVYY23";

        TestClient client = new TestClient(config, login, pass);
        client.start();

        client.awaitForLogin();

        for (;;) {
            Thread.sleep(100);
        }


    }

}
