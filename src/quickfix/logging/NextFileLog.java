package quickfix.logging;

import kz.kase.fix.FixProtocol;
import quickfix.SessionID;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class NextFileLog extends AbstractLog {

    public static final String MESS_TYPE = "35";
    FileWriter out;
    public static final String DEF_DIR_PATH = System.getProperty("user.dir");
    String filePath;
    SessionID sesId;
    Filter filter;


    public NextFileLog(SessionID sesId, String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            this.filePath = filePath;
            this.sesId = sesId;
        } else {
            this.filePath = DEF_DIR_PATH;
        }
        try {
            prepareLogToWork(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        filter = Filter.ORDERS;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    private void prepareLogToWork(String filePath) throws IOException {
        File logDir = new File(filePath);
        boolean dirsCreated = true;

        if (!logDir.exists()) {
            if (!logDir.mkdirs()) {
                throw new IOException(logDir + " cud not create log dir " + logDir);
            }
        }
    }

    public File createFile(String mess) throws IOException {
        if (mess == null || mess.isEmpty()) return null;
        int hash = Math.abs(mess.hashCode());
        File newFile = new File(filePath + "/" + hash);
        if (newFile.createNewFile()) return newFile;
        return null;
    }

    public void setLogFile(String filePath) {
        try {
            prepareLogToWork(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean filter(String message) {
        switch (filter) {
            case ORDERS:
                if (isNeedType(message, FixProtocol.MESSAGE_ORDER_CANCEL_REQUEST,
                        FixProtocol.MESSAGE_NEW_ORDER_SINGLE)) return true;
            case QUOTES:
            case MD:
                return false;

        }
        return false;
    }

    private boolean isNeedType(String message, String... types) {
        String[] splited = message.split("\001");
        for (String s : splited) {
            if (s.startsWith(MESS_TYPE)) {
                String[] tmp = s.split("=");
                if (types != null) {
                    for (String t : types) {
                        if (tmp[1].equals(t)) return true;
                    }

                }
            }
        }
        return false;
    }

    @Override
    protected void logIncoming(String message) {

    }

    @Override
    protected void logOutgoing(String message) {
        if (message != null && !message.isEmpty()) {
            if (filter(message)) {
                try {
                    File file = createFile(message);
                    out = new FileWriter(file);
                    out.write(message);
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void clear() {

    }

    @Override
    public void onEvent(String text) {
    }

    @Override
    public void onErrorEvent(String text) {

    }


    public static enum Filter {
        ORDERS, DEALS, QUOTES, MD
    }

}
