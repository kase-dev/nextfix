package kz.kase.fix.messages;

import quickfix.Message;

import java.util.Date;

import static kz.kase.fix.FixProtocol.*;

public class Email extends Message {

    public Email() {
        super();
        getHeader().setString(FIELD_MESSAGE_TYPE, MESSAGE_EMAIL);
    }

    public void setSubject(String subj) {
        setString(FIELD_SUBJECT, subj);
    }

    public void setMessage(String message) {
        setString(FIELD_MESSAGE, message);
    }

    public void setTime(Date time) {
        setUtcTimeStamp(FIELD_ORIG_TIME, time);
    }

    public String getSubject() {
        return getString(FIELD_SUBJECT);
    }

    public String getMessage() {
        return getString(FIELD_MESSAGE);
    }

    public Date getTime() {
        return getUtcTimeStamp(FIELD_ORIG_TIME);
    }

}
