package quickfix.mina.ssl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.FileUtil;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SSL context factory that deals with Keystores.
 * Caches the created SSL contexts for future reuse.
 */

public class SSLContextFactory {

    private static final Logger log = LoggerFactory.getLogger(SSLContextFactory.class);
    private static final String PROTOCOL = "TLS";
    private static final String KEY_MANAGER_FACTORY_ALGORITHM;
    private static final Map<String, SSLContext> contextCache = new ConcurrentHashMap<>();

    static {
        KEY_MANAGER_FACTORY_ALGORITHM = getSecurityProperty("ssl.KeyManagerFactory.algorithm", "SunX509");
    }

    private static String getSecurityProperty(String key, String defaultValue) {
        String value = Security.getProperty(key);
        return value == null ? defaultValue : value;
    }

    /**
     * Creates an {@link SSLContext} with a specified keystore and password for that keystore
     */
    public static SSLContext getInstance(String keyStoreName, char[] keyStorePassword,
                                         String trustStore, char[] trustStorePassword)
            throws GeneralSecurityException {
        SSLContext context = contextCache.get(keyStoreName);
        if (context == null) {
            synchronized (contextCache) {
                if (context == null) {
                    try {
                        context = createSSLContext(keyStoreName, keyStorePassword, trustStore, trustStorePassword);
                        contextCache.put(keyStoreName, context);
                    } catch (Exception ioe) {
                        throw new GeneralSecurityException("Can't create SSLContext:" + ioe);
                    }
                }
            }
        }

        return context;
    }

    private static SSLContext createSSLContext(String keyStoreName, char[] keyStorePassword,
                                               String trustKey, char[] trustPass)
            throws GeneralSecurityException, IOException {
        KeyManagerFactory kmf = initializeKeyManager(keyStoreName, keyStorePassword);

        TrustManagerFactory tmf = initializeTrustManager(trustKey, trustPass);
        SSLContext sslContext = SSLContext.getInstance(PROTOCOL);
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        return sslContext;
    }

    private static KeyManagerFactory initializeKeyManager(String keyStoreName,
                                                          char[] keyStorePassword) throws KeyStoreException, IOException,
            NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
        KeyStore ks = initializeKeyStore(keyStoreName, keyStorePassword);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KEY_MANAGER_FACTORY_ALGORITHM);
        kmf.init(ks, keyStorePassword);
        return kmf;

    }

    private static TrustManagerFactory initializeTrustManager(String keyStoreName,
                                                              char[] keyStorePassword) throws KeyStoreException, IOException,
            NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
        KeyStore ks = initializeKeyStore(keyStoreName, keyStorePassword);
        TrustManagerFactory kmf = TrustManagerFactory.getInstance(KEY_MANAGER_FACTORY_ALGORITHM);
        kmf.init(ks);
        return kmf;
    }

    private static KeyStore initializeKeyStore(String keyStoreName, char[] keyStorePassword)
            throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        InputStream in = null;
        try {
            in = FileUtil.open(SSLContextFactory.class, keyStoreName);
            if (in == null) {
                log.warn(keyStoreName + ": keystore not found, using empty keystore");
            }
            keyStore.load(in, keyStorePassword);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                }
            }
        }
        return keyStore;
    }
}
