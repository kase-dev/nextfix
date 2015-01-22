package kz.kase.fix.test.func;


import kz.kase.fix.ManualSessionProvider;
import kz.kase.fix.OrderType;
import kz.kase.fix.Side;
import kz.kase.fix.TimeInForce;
import kz.kase.fix.core.FixUtils;
import kz.kase.fix.factory.KaseFixMessageFactory;
import kz.kase.fix.messages.NewOrderSingle;
import kz.kase.fix.messages.OrderPriceType;
import kz.kase.fix.test.SimpleSSLTransport;
import org.junit.Test;
import quickfix.*;
import quickfix.logging.LogFactory;
import quickfix.logging.ScreenLogFactory;
import quickfix.mina.acceptor.AbstractSocketAcceptor;
import quickfix.store.FileStoreFactory;
import quickfix.store.MessageStoreFactory;

import java.io.FileNotFoundException;

public class SSLTest {

    public static final String USER_DIR = System.getProperty("user.dir");

    private ManualSessionProvider provider;
    public static final String SERVER_CONFIG = USER_DIR + "/server-dyn.cfg";
    public static final String CLIENT_CONFIG = USER_DIR + "/client-dyn.cfg";


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
    public void startServerAndClientAndSendMess() throws Exception {

        String username = "U234401";
        String pass = "123";

        starServer(SERVER_CONFIG, username);

        ClientApp app = startClient(CLIENT_CONFIG, username, pass);

        app.sendMessage(makeNewOrder());
        Thread.sleep(2500);
    }

    @Test
    public void startServerAndRemoteClient()
            throws FileNotFoundException, ConfigError, InterruptedException {

        String username = "U234401";
//        String pass = "123";
        starServer(SERVER_CONFIG, username);

        SimpleSSLTransport sst = new SimpleSSLTransport();
/*
        sst.sslClientInit(KEY_STORE_JKS,
                KEY_PASS, SERVER_HOST, SERVER_PORT);
*/

        sst.sendMessageToServer("8=FIXT.1.1\u00019=101\u000135=A\u000134=1\u000149=U234401\u000152=20140827-05:57:47.837\u000156=TradeGW\u000198=0\u0001108=5\u0001141=Y\u0001553=U234401\u0001554=123\u00011137=7\u000110=173");

       Thread.sleep(2000);
    }

    private void starServer(String serverConfig, String username)
            throws FileNotFoundException, ConfigError, InterruptedException {
        initServer(serverConfig, username);

        Thread.sleep(500);

        addSession(username);

    }

    private ClientApp startClient(String clientConfig, String username, String pass) throws FileNotFoundException, ConfigError, InterruptedException {
        ClientApp app = initClient(clientConfig, username, pass);
        //        initClient(clientConfig, usernameCorrect, pass);
        Thread.sleep(2500);
        app.awaitLogon();
        return app;
    }

    private NewOrderSingle makeNewOrder() {
        long ref = 435;
        long accId = 10;
        long instrId = 10;
        double price = 34.01;
        long qty = 1000;
        Side side = Side.BUY;
        String symbol = "KZT";
        String accName = "F_000_2";

        String comment = "Tralala";
        double spread = 3.4D;

        return new NewOrderSingle()
                .setRef(ref)
                .setOrderType(OrderType.LIMIT)
                .setPrice(price)
                .setQty(qty)
                .setSide(side)
                .setTimeInForce(TimeInForce.DAY)
                .setComment(comment)
                .setSymbol(symbol)
                .setAccName(accName)
                .setMarketMakerType(true)
                .setSpread(spread)
                .setDoubleOrderType(true)
                .setPriceType(OrderPriceType.AVERAGE_PRICE);
    }


}
