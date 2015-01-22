package kz.kase.fix.test.func;

import quickfix.*;
import quickfix.logging.LogFactory;
import quickfix.logging.ScreenLogFactory;
import quickfix.mina.acceptor.AbstractSocketAcceptor;
import quickfix.store.FileStoreFactory;
import quickfix.store.MessageStoreFactory;

import java.util.concurrent.CountDownLatch;

public class FixServer {

    private final AbstractSocketAcceptor acceptor;
    private final CountDownLatch shutdownLatch = new CountDownLatch(1);


    public FixServer(Application app, MessageFactory mesFactory, SessionSettings settings)
            throws ConfigError {

        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new ScreenLogFactory(true, true, true, false);

//        acceptor = new SocketAcceptor(app, storeFactory, settings, logFactory, mesFactory);
        acceptor = new ThreadedSocketAcceptor(app, storeFactory, settings, logFactory, mesFactory);

    }

    public void start() throws ConfigError {
        acceptor.start();
    }

    public void stop() {
        acceptor.stop();
        shutdownLatch.countDown();
    }

    public void awaitShutdown() throws InterruptedException {
        shutdownLatch.await();
    }

}
