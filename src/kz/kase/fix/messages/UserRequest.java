package kz.kase.fix.messages;

import kz.kase.fix.FixProtocol;
import kz.kase.fix.UserRequestType;
import quickfix.Message;

import static kz.kase.fix.FixProtocol.FIELD_NEW_PASSWORD;

public class UserRequest extends Message{
    public UserRequest() {
        super();
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_USER_REQUEST);
    }

    public UserRequest setRef(long ref) {
        setLong(FixProtocol.FIELD_USER_REQ_ID, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FixProtocol.FIELD_USER_REQ_ID);
    }

    public boolean hasRef() {
        return isSetField(FixProtocol.FIELD_USER_REQ_ID);
    }

    public UserRequest setReqType(UserRequestType reqType) {
        setInt(FixProtocol.FIELD_USER_REQ_TYPE, reqType.getValue());
        return this;
    }

    public Integer getReqType() {
        return getInt(FixProtocol.FIELD_USER_REQ_TYPE);
    }

    public boolean hasReqType() {
        return isSetField(FixProtocol.FIELD_USER_REQ_TYPE);
    }

    public UserRequest setUsername(String username) {
        setString(FixProtocol.FIELD_USERNAME, username);
        return this;
    }

    public String getUsername() {
        return getString(FixProtocol.FIELD_USERNAME);
    }

    public boolean hasUsername() {
        return isSetField(FixProtocol.FIELD_USERNAME);
    }

    public UserRequest setPassword(String password) {
        setString(FixProtocol.FIELD_PASSWORD, password);
        return this;
    }


    public String getPassword() {
        return getString(FixProtocol.FIELD_PASSWORD);
    }

    public boolean hasPassword() {
        return isSetField(FixProtocol.FIELD_PASSWORD);
    }


    public UserRequest setNewPass(String newPass) {
        setString(FIELD_NEW_PASSWORD, newPass);
        return this;
    }

    public String getNewPass() {
        return getString(FIELD_NEW_PASSWORD);
    }

    public boolean hasNewPass() {
        return isSetField(FIELD_NEW_PASSWORD);
    }
}
