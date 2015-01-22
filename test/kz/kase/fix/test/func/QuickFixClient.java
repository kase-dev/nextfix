package kz.kase.fix.test.func;

import kz.kase.fix.factory.KaseFixMessageFactory;
import quickfix.*;
import quickfix.logging.LogFactory;
import quickfix.logging.ScreenLogFactory;
import quickfix.store.FileStoreFactory;
import quickfix.store.MessageStoreFactory;

import java.io.FileNotFoundException;


public class QuickFixClient {

    private static long ref = 0;
    private final SocketInitiator initiator;
    private final ClientApp clientApp;
    private final SessionSettings settings;

    public QuickFixClient(ClientApp app, SessionSettings settings)
            throws ConfigError, FileNotFoundException {

        this.clientApp = app;
        this.settings = settings;

        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new ScreenLogFactory(true, true, true, false);
        MessageFactory mesFactory = new KaseFixMessageFactory();

        initiator = new SocketInitiator(clientApp, storeFactory, settings, logFactory, mesFactory);
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

}
