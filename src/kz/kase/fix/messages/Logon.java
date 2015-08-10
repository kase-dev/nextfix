package kz.kase.fix.messages;

import kz.kase.fix.EncryptMethod;
import kz.kase.fix.FixProtocol;
import quickfix.Message;

import static kz.kase.fix.FixProtocol.*;

public class Logon extends Message {

    public Logon() {
        super();
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_LOGON);
    }

    public Logon setRef(long ref) {
        setLong(FixProtocol.FIELD_SEC_REQ_ID, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FixProtocol.FIELD_SEC_REQ_ID);
    }

    public boolean hasRef() {
        return isSetField(FixProtocol.FIELD_SEC_REQ_ID);
    }

    public Logon setHeartbeatInt(int hbInt) {
        setInt(FixProtocol.FIELD_HEARTBEAT_INT, hbInt);
        return this;
    }

    public Integer getHeartbeatInt() {
        return getInt(FixProtocol.FIELD_HEARTBEAT_INT);
    }

    public boolean hasHeartbeatInt() {
        return isSetField(FixProtocol.FIELD_HEARTBEAT_INT);
    }


    public Logon setUsername(String username) {
        setString(FixProtocol.FIELD_USERNAME, username);
        return this;
    }

    public String getUsername() {
        return getString(FixProtocol.FIELD_USERNAME);
    }

    public boolean hasUsername() {
        return isSetField(FixProtocol.FIELD_USERNAME);
    }


    public Logon setPassword(String password) {
        setString(FixProtocol.FIELD_PASSWORD, password);
        return this;
    }


    public String getPassword() {
        return getString(FixProtocol.FIELD_PASSWORD);
    }

    public boolean hasPassword() {
        return isSetField(FixProtocol.FIELD_PASSWORD);
    }


    public Logon setEncryptMethod(EncryptMethod method) {
        setInt(FixProtocol.FIELD_ENCRYPT_METHOD, method.getValue());
        return this;
    }

    public EncryptMethod getEncryptMethod() {
        return EncryptMethod.valueOf(getInt(FixProtocol.FIELD_ENCRYPT_METHOD));
    }

    public boolean hasEncryptMethod() {
        return isSetField(FixProtocol.FIELD_ENCRYPT_METHOD);
    }


    public Logon setResetSeqNum(boolean reset) {
        setBoolean(FIELD_RESET_SEQ_NUM, reset);
        return this;
    }

    public Boolean getResetSeqNum() {
        return getBoolean(FIELD_RESET_SEQ_NUM);
    }

    public boolean hasResetSeqNum() {
        return isSetField(FIELD_RESET_SEQ_NUM);
    }

    public Logon setMemberName(String memberName) {
        setString(FIELD_MEMBER_NAME, memberName);
        return this;
    }

    public String getMemberName() {
        return getString(FIELD_MEMBER_NAME);
    }

    public boolean hasMemberName() {
        return isSetField(FIELD_MEMBER_NAME);
    }

    public Logon setEncryptedPassMethod(int method) {
        setInt(FIELD_ENCRYPTED_PASSWORD_METHOD, method);
        return this;
    }

    public Integer getEncryptPassMethod() {
        return getInt(FIELD_ENCRYPTED_PASSWORD_METHOD);
    }

    public boolean hasEncryptPassMethod() {
        return isSetField(FIELD_ENCRYPTED_PASSWORD_METHOD);
    }

    public Logon setEncryptPassLen(int len) {
        setInt(FIELD_ENCRYPTED_PASSWORD_LEN, len);
        return this;
    }

    public Integer getEncryptPassLen() {
        return getInt(FIELD_ENCRYPTED_PASSWORD_LEN);
    }

    public boolean hasEncryptPassLen() {
        return isSetField(FIELD_ENCRYPTED_PASSWORD_LEN);
    }

    public Logon setEncryptPass(String pass) {
        setString(FIELD_ENCRYPTED_PASSWORD, pass);
        return this;
    }

    public String getEncryptPass() {
        return getString(FIELD_ENCRYPTED_PASSWORD);
    }

    public boolean hasEncryptPass() {
        return isSetField(FIELD_ENCRYPTED_PASSWORD);
    }

    public Logon setEncryptNewPass(String pass) {
        setString(FIELD_ENCRYPTED_NEW_PASSWORD, pass);
        return this;
    }

    public String getEncryptNewPass() {
        return getString(FIELD_ENCRYPTED_NEW_PASSWORD);
    }

    public boolean hasEncryptNewPass() {
        return isSetField(FIELD_ENCRYPTED_NEW_PASSWORD);
    }

    public Logon setEncryptNewPassLen(int len) {
        setInt(FIELD_ENCRYPTED_NEW_PASSWORD_LEN, len);
        return this;
    }

    public Integer getEncryptNewPassLen() {
        return getInt(FIELD_ENCRYPTED_NEW_PASSWORD_LEN);
    }

    public boolean hasEncryptNewPassLen() {
        return isSetField(FIELD_ENCRYPTED_NEW_PASSWORD_LEN);
    }


    public Logon setText(String text) {
        setString(FixProtocol.FIELD_TEXT, text);
        return this;
    }

    public String getText() {
        return getString(FixProtocol.FIELD_TEXT);
    }

    public boolean hasText() {
        return isSetField(FixProtocol.FIELD_TEXT);
    }

}
