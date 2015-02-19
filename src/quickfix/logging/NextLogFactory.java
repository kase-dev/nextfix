package quickfix.logging;

import quickfix.RuntimeError;
import quickfix.SessionID;
import quickfix.SessionSettings;

public class NextLogFactory implements LogFactory {
    public static final String SETTING_FILE_LOG_PATH = "FileLogPath";

    /**
     * Specify whether to include milliseconds in log output time stamps. Off, by
     * default.
     */
    public static final String SETTING_INCLUDE_MILLIS_IN_TIMESTAMP = "FileIncludeMilliseconds";

    /**
     * Specify whether to include time stamps for message input and output. Off, by
     * default.
     */
    public static final String SETTING_INCLUDE_TIMESTAMP_FOR_MESSAGES = "FileIncludeTimeStampForMessages";

    /**
     * Specify whether to include time stamps for message input and output. Off, by
     * default.
     */
    public static final String SETTING_LOG_HEARTBEATS = "FileLogHeartbeats";


    protected final SessionSettings settings;

    public NextLogFactory(SessionSettings settings) {
        this.settings = settings;
    }

    @Override
    public Log create() {
        return null;
    }

    @Override
    public Log create(SessionID sessionID) {
        try {
            if (settings.isSetting(FileLogFactory.SETTING_FILE_LOG_PATH)) {
                return new NextFileLog(sessionID, settings.getString(FileLogFactory.SETTING_FILE_LOG_PATH));
            }
        } catch (Exception e) {
            throw new RuntimeError(e);
        }
        return null;
    }


}
