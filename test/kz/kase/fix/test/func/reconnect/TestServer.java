package kz.kase.fix.test.func.reconnect;

import kz.kase.fix.ManualSessionProvider;
import kz.kase.fix.core.FixUtils;
import kz.kase.fix.factory.KaseFixMessageFactory;
import quickfix.*;
import quickfix.logging.LogFactory;
import quickfix.logging.ScreenLogFactory;
import quickfix.store.FileStoreFactory;
import quickfix.store.MessageStoreFactory;

import java.io.FileNotFoundException;
import java.util.concurrent.CountDownLatch;

public class TestServer {

    private final ThreadedSocketAcceptor acceptor;
    private final CountDownLatch shutdownLatch = new CountDownLatch(1);
    private ManualSessionProvider provider;

    public TestServer(String fixConfig) throws FileNotFoundException, ConfigError {
        SessionSettings settings = FixUtils.getSettings(fixConfig);
        MessageFactory mesFactory = new KaseFixMessageFactory();

        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new ScreenLogFactory(true, true, true, false);
        Application app = new ServerApp();

        acceptor = new ThreadedSocketAcceptor(app, storeFactory, settings, logFactory, mesFactory);
    }


    public void start() throws ConfigError {
        acceptor.start();
        provider = acceptor.getProvider();
    }

    public void addUserSession(String username) throws ConfigError {
        if (provider != null) {
            provider.addSession(username);
        }
    }

    public void stop() {
        acceptor.stop();
        shutdownLatch.countDown();
    }

    public void awaitShutdown() throws InterruptedException {
        shutdownLatch.await();
    }


    public static void main(String[] args) throws FileNotFoundException, ConfigError {
        String config = "test-server-config.cfg";
        TestServer server = new TestServer(config);

        server.start();
    }
}
