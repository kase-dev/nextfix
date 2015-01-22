package kz.kase.fix.messages;

import kz.kase.fix.FixProtocol;
import quickfix.Message;

import static kz.kase.fix.FixProtocol.FIELD_TEXT;

public class Logout extends Message {


    public Logout() {
        this(false);
    }

    public Logout(boolean parse) {
        super();
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_LOGOUT);
    }


    public Logout setText(String text) {
        setString(FIELD_TEXT, text);
        return this;
    }

    public String getText() {
        return getString(FIELD_TEXT);
    }

    public boolean hasText() {
        return isSetField(FIELD_TEXT);
    }


}
