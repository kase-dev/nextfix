package kz.kase.fix.messages;

import kz.kase.fix.FixProtocol;
import kz.kase.fix.UserRequestType;
import kz.kase.fix.UserStatus;
import quickfix.Message;

import static kz.kase.fix.FixProtocol.FIELD_NEW_PASSWORD;
import static kz.kase.fix.FixProtocol.FIELD_USER_STATUS;

public class UserResponse extends Message{
    public UserResponse() {
        super();
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_USER_RESP);
    }

    public UserResponse setRef(long ref) {
        setLong(FixProtocol.FIELD_USER_REQ_ID, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FixProtocol.FIELD_USER_REQ_ID);
    }

    public boolean hasRef() {
        return isSetField(FixProtocol.FIELD_USER_REQ_ID);
    }

    public UserResponse setUsername(String username) {
        setString(FixProtocol.FIELD_USERNAME, username);
        return this;
    }

    public String getUsername() {
        return getString(FixProtocol.FIELD_USERNAME);
    }

    public boolean hasUsername() {
        return isSetField(FixProtocol.FIELD_USERNAME);
    }

    public UserResponse setUserStatus(UserStatus status) {
        setInt(FIELD_USER_STATUS, status.getValue());
        return this;
    }

    public Integer getUserStatus() {
        return getInt(FIELD_USER_STATUS);
    }

    public boolean hasUserStatus() {
        return isSetField(FIELD_USER_STATUS);
    }
}
