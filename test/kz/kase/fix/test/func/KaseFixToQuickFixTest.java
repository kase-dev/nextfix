package kz.kase.fix.test.func;

import kz.kase.fix.*;
import kz.kase.fix.core.FixUtils;
import kz.kase.fix.factory.KaseFixMessageFactory;
import kz.kase.fix.messages.Logon;
import kz.kase.fix.messages.NewOrderSingle;
import org.junit.Before;
import org.junit.Test;
import org.quickfixj.CharsetSupport;
import quickfix.*;

import java.io.IOException;

public class KaseFixToQuickFixTest {

    public static final int PORT = 9880;
    public static final String HOST = "localhost";

    public static final String USER_DIR = System.getProperty("user.dir");
    public static final String PROJ_HOME = USER_DIR;

    public static final String CONFIG_FILE = "fix-config.cfg";

    private KaseFixClient client;


    @Before
    public void init() throws Exception {
        String encoding = CharsetSupport.getCharset();
        DataDictionary dictionary = FixUtils.createDictionary(PROJ_HOME + "/" + CONFIG_FILE);
        MessageFactory factory = new KaseFixMessageFactory();

        ServerApp serverApp = new ServerApp();
        SessionSettings settings = FixUtils.getSettings(PROJ_HOME + "/" + CONFIG_FILE);
        FixServer fixServer = new FixServer(serverApp, factory, settings);
        fixServer.start();

        Thread.sleep(500);

        client = new KaseFixClient(dictionary, factory, encoding);
        client.connect(HOST, PORT);
        client.startThreads();

        Thread.sleep(500);
    }

    @Test
    public void testFix() throws IOException, InterruptedException {
        testLogon();

        Thread.sleep(2000);

        testLimitOrder();

        Thread.sleep(600000);
    }

    private void testLogon() throws IOException {
        String login = "U001";
        String pass = "123";
        int heartbeat = 30;

        System.out.println("Sending logon request: ");
        System.out.println("\tlogin: " + login);
        System.out.println("\tpass: " + pass);
        System.out.println();

        Logon logon = new Logon()
                .setUsername(login)
                .setPassword(pass)
                .setHeartbeatInt(heartbeat)
                .setEncryptMethod(EncryptMethod.NONE_OTHER);

        client.sendMessage(logon);
    }


    private void testLimitOrder() throws IOException {
        long accId = 3;
        String account = "A000001";
        long ref = 2;
        String symbol = "I000001";
        int qty = 150;
        double price = 30.34;
        Side side = Side.SELL;

        NewOrderSingle order = new NewOrderSingle()
                .setRef(ref)
                .setOrderType(OrderType.LIMIT)
                .setSymbol(symbol)
                .setPrice(price)
                .setQty(qty)
                .setSide(side)
                .setTimeInForce(TimeInForce.DAY);

        client.sendMessage(order);
    }





}
