package kz.kase.fix.test.func;

import kz.kase.fix.core.FixUtils;
import kz.kase.fix.factory.KaseFixMessageFactory;
import org.junit.Before;
import org.junit.Test;
import quickfix.MessageFactory;
import quickfix.SessionSettings;

import java.io.IOException;

public class ClientServerTest {


    public static final String USER_DIR = System.getProperty("user.dir");
    public static final String PROJ_HOME = USER_DIR;

    public static final String SERVER_CONFIG_FILE = "fix-config.cfg";
    public static final String CLIENT_CONFIG_FILE = "fix-client-config.cfg";

    private QuickFixClient fixClient;


    @Before
    public void init() throws Exception {
        String serverConfig = PROJ_HOME + "/" + SERVER_CONFIG_FILE;
        String clientConfig = PROJ_HOME + "/" + CLIENT_CONFIG_FILE;

        String login = "U001";
        String pass = "123";


        initServer(serverConfig);

        Thread.sleep(3000);

        initClient(clientConfig, login, pass);

        Thread.sleep(500);
    }


    private void initServer(String configPath) throws Exception {
        System.out.println("Starting server...");
        ServerApp serverApp = new ServerApp();
        MessageFactory factory = new KaseFixMessageFactory();
        SessionSettings settings = FixUtils.getSettings(configPath);

        FixServer fixServer = new FixServer(serverApp, factory, settings);
        fixServer.start();
        System.out.println("Server is started.");
    }


    private void initClient(String configPath, String login, String pass) throws Exception {
        System.out.println("Starting client...");

        ClientApp fixApp = new ClientApp(login, pass);
        SessionSettings settings = FixUtils.getSettings(configPath);

        fixClient = new QuickFixClient(fixApp, settings);
        fixClient.start();

        System.out.println("Client is started, waiting for logon...");

        fixApp.awaitLogon();

        System.out.println("Client is logged on.");
    }


    @Test
    public void testFix() throws IOException, InterruptedException {

        Thread.sleep(5000);

    }










}
