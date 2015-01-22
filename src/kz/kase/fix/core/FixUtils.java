package kz.kase.fix.core;

import kz.kase.fix.FixProtocol;
import org.quickfixj.CharsetSupport;
import quickfix.*;

import java.io.*;
import java.util.Properties;

public class FixUtils {


    public static SessionSettings getSettings(String config)
            throws FileNotFoundException, ConfigError {
        InputStream in = null;
        try {
            in = new FileInputStream(config);
            return new SessionSettings(in);
        } finally {

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void setupEncoding(String encoding) {
        try {
            CharsetSupport.setCharset(encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static DataDictionary createDictionary(String settingsFile) throws ConfigError, FieldConvertError {
        SessionSettings settings = new SessionSettings(settingsFile);

        String path;
        SessionID sessionID = new SessionID("", "", "");
        String setKey = Session.SETTING_DATA_DICTIONARY;
        if (settings.isSetting(sessionID, setKey)) {
            path = settings.getString(sessionID, setKey);
        } else {
            throw new IllegalStateException(setKey + " is not defined in settings file!");
        }

        return new DataDictionary(path);
    }


    public static void initializeHeader(Message.Header header,
                                        String beginString,
                                        String senderCompID,
                                        String senderSubID,
                                        String senderLocationID,
                                        String targetCompID,
                                        String targetSubID,
                                        String targetLocationID,
                                        int expectedSenderNum) {
        header.setString(FixProtocol.FIELD_BEGIN_STRING, beginString);
        header.setString(FixProtocol.FIELD_SENDER_COMP_ID, senderCompID);
        optionallySetID(header, FixProtocol.FIELD_SENDER_SUB_ID, senderSubID);
        optionallySetID(header, FixProtocol.FIELD_SENDER_LOCATION_ID, senderLocationID);
        header.setString(FixProtocol.FIELD_TARGET_COMP_ID, targetCompID);
        optionallySetID(header, FixProtocol.FIELD_TARGET_SUB_ID, targetSubID);
        optionallySetID(header, FixProtocol.FIELD_TARGET_LOCATION_ID, targetLocationID);
        header.setInt(FixProtocol.FIELD_MSG_SEQ_NUM, expectedSenderNum);
        insertSendingTime(header, true);
    }

    private static void optionallySetID(Message.Header header, int field, String value) {
        if (!value.equals(SessionID.NOT_SET)) {
            header.setString(field, value);
        }
    }

    private static void insertSendingTime(Message.Header header, boolean includeMillis) {
        header.setUtcTimeStamp(FixProtocol.FIELD_SENDING_TIME, SystemTime.getDate(), includeMillis);
    }


//    public static void addSession(SessionSettings settings, String username) throws ConfigError {
//        Properties props = settings.getDefaultProperties();
//        String beginString = props.getProperty(SessionSettings.BEGINSTRING);
//        String targetComp = props.getProperty(SessionSettings.TARGETCOMPID);
//        SessionID sessionID = new SessionID(beginString, username, targetComp);
//
//        settings.set(sessionID, new Dictionary(null, props));
//    }

    public static Properties copyProps(Properties props) {
        Properties res = new Properties();
        for (Object k : props.keySet()) {
            String key = (String) k;
            res.setProperty(key, props.getProperty(key));
        }
        return res;
    }

    public static SessionID addAcceptorSession(SessionSettings settings, String targetCompID) throws ConfigError {
        Properties defaults = settings.getDefaultProperties();

        Properties props = copyProps(defaults);
//        Properties props = new Properties(defaults);

        String beginString = props.getProperty(SessionSettings.BEGINSTRING);
        String senderCompID = props.getProperty(SessionSettings.SENDERCOMPID);
        String senderSubID = props.getProperty(SessionSettings.SENDERSUBID);
        String senderLocationID = props.getProperty(SessionSettings.SENDERLOCID);

        if (targetCompID == null) {
            targetCompID = props.getProperty(SessionSettings.TARGETCOMPID);
        } else {
            props.setProperty(SessionSettings.TARGETCOMPID, targetCompID);
        }

        String targetSubID = props.getProperty(SessionSettings.TARGETSUBID);
        String targetLocationID = props.getProperty(SessionSettings.TARGETLOCID);
        String sessionQualifier = props.getProperty(SessionSettings.SESSION_QUALIFIER);

        final SessionID sessionId = new SessionID(beginString, senderCompID,
                senderSubID, senderLocationID, targetCompID,
                targetSubID, targetLocationID, sessionQualifier);

        settings.set(sessionId, new Dictionary("session.props", props));

        return sessionId;
    }

    public static void addSession(SessionSettings settings, String username) throws ConfigError {
        Properties props = settings.getDefaultProperties();
        String beginString = props.getProperty(SessionSettings.BEGINSTRING);
        String targetComp = props.getProperty(SessionSettings.TARGETCOMPID);
        SessionID sessionID = new SessionID(beginString, username, targetComp);

        settings.set(sessionID, new Dictionary(null, props));
    }

}
