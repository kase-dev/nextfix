package kz.kase.fix.test;

import javax.net.ssl.*;
import java.io.*;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;

//for one connection (for one client) test

public class SimpleSSLTransport {

    public static final int SERVER_PORT = 9880;
    public static final String SERVER_HOST = "localhost";
    public static final String APP_DIR = System.getProperty("user.dir");
    public static final String KEY_STORE_JKS = APP_DIR + "/test-store.jks";
    public static final String KEY_PASS = "123456";
    public static final String TRUST_STORE_JKS = APP_DIR + "/etc/ssl/kase_ssl.jks";
    public static final String TRUST_KEY_PASS = "kasenextmd";

    private SSLSocket clientSocket;
    private Socket serverSocket;
    private InputStream serverInput;
    private OutputStream clientOutput;
    private static boolean stopServer;

    public static void main(String[] args) {
        SimpleSSLTransport sslTest = new SimpleSSLTransport();
        sslTest.sslServerInit(KEY_STORE_JKS, KEY_PASS, TRUST_STORE_JKS, TRUST_KEY_PASS);

        sslTest.sslClientInit(TRUST_STORE_JKS, TRUST_KEY_PASS, KEY_STORE_JKS,
                KEY_PASS, SERVER_HOST, SERVER_PORT);

        sslTest.sendMessageToServer("8=FIXT.1.1\u00019=101\u000135=A\u000134=1\u000149=U234401\u000152=20140827-05:57:47.837\u000156=TradeGW\u000198=0\u0001108=5\u0001141=Y\u0001553=U234401\u0001554=123\u00011137=7\u000110=173");
//        sslTest.stopServer();
    }

    public void stopServer() {
        stopServer = true;
    }

    public void sslServerInit(String key, String pass, String trustStore, String trustPass) {

        SSLContext sc = null;
        try {
            sc = getSslContext(key, pass, trustStore, trustPass);
        } catch (KeyStoreException |
                IOException | NoSuchAlgorithmException |
                CertificateException |
                UnrecoverableKeyException |
                KeyManagementException e) {
            e.printStackTrace();
        }

        assert sc != null;
        SSLServerSocketFactory ssf = sc.getServerSocketFactory();
        try {
            final SSLServerSocket s = (SSLServerSocket) ssf.createServerSocket(SERVER_PORT);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        SSLSocket ss = null;
                        BufferedReader reader = null;
                        for (; ; ) {
                            if (stopServer) break;
                            if (reader == null || !ss.isConnected()) {

                                System.out.println("Awaiting for connection ...");
                                ss = (SSLSocket) s.accept();
                                System.out.println("Got connection from : " + ss.getRemoteSocketAddress());

                                serverInput = ss.getInputStream();
                                reader = new BufferedReader(new InputStreamReader(serverInput));
                            } else {
                                String line = reader.readLine();
                                if (line != null) {
                                    System.out.println("Server receiving message: " + line);
                                }
                            }
                            Thread.sleep(300);
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sslClientInit(String key, String pass, String trustStore, String trustPass, String host, int port) {
        SSLContext sc = null;
        try {
            sc = getSslContext(key, pass, trustStore, trustPass);
        } catch (KeyStoreException | IOException |
                NoSuchAlgorithmException |
                CertificateException |
                UnrecoverableKeyException |
                KeyManagementException e) {
            e.printStackTrace();
        }

        assert sc != null;
        SSLSocketFactory ssf = sc.getSocketFactory();

        try {
            clientSocket = (SSLSocket) ssf.createSocket(host, port);
            clientOutput = clientSocket.getOutputStream();
            System.out.println("Starting handshake...");
            clientSocket.startHandshake();
            System.out.println("Handshake done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendMessageToServer(String message) {
        try {
            System.out.println("Client sending message: " + message);
            clientOutput.write(message.getBytes());
            clientOutput.flush();
            clientOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private SSLContext getSslContext(String keyStore, String keyStorePass,
                                     String trustStore, String trustStorePass) throws
            KeyStoreException, IOException,
            NoSuchAlgorithmException, CertificateException,
            UnrecoverableKeyException, KeyManagementException {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(keyStore), keyStorePass.toCharArray());

        KeyStore ts = KeyStore.getInstance("JKS");
        ts.load(new FileInputStream(trustStore), trustStorePass.toCharArray());

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, keyStorePass.toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ts);

        SSLContext sc = SSLContext.getInstance("TLS");
        TrustManager[] trustManagers = tmf.getTrustManagers();
        sc.init(kmf.getKeyManagers(), trustManagers, null);
        return sc;
    }

}
