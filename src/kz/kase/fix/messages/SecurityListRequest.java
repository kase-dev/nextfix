package kz.kase.fix.messages;

import kz.kase.fix.FixProtocol;
import kz.kase.fix.SecurityListRequestType;
import quickfix.Message;

public class SecurityListRequest extends Message {

    public SecurityListRequest() {
        super();
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_SEC_LIST_REQUEST);
    }

    public SecurityListRequest setRef(long ref) {
        setLong(FixProtocol.FIELD_SEC_REQ_ID, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FixProtocol.FIELD_SEC_REQ_ID);
    }

    public boolean hasRef() {
        return isSetField(FixProtocol.FIELD_SEC_REQ_ID);
    }

    public SecurityListRequest setType(SecurityListRequestType type) {
        setInt(FixProtocol.FIELD_SEC_LIST_REQ_TYPE, type.getValue());
        return this;
    }

    public SecurityListRequestType getType() {
        return SecurityListRequestType.valueOf(getInt(FixProtocol.FIELD_SEC_LIST_REQ_TYPE));
    }

    public boolean hasType() {
        return isSetField(FixProtocol.FIELD_SEC_LIST_REQ_TYPE);
    }


    public SecurityListRequest setSymbol(String symbol) {
        setString(FixProtocol.FIELD_SYMBOL, symbol);
        return this;
    }

    public String getSymbol() {
        return getString(FixProtocol.FIELD_SYMBOL);
    }

    public boolean hasSymbol() {
        return isSetField(FixProtocol.FIELD_SYMBOL);
    }


    public SecurityListRequest setSecurityId(Long secId) {
        setLong(FixProtocol.FIELD_SECURITY_ID, secId);
        return this;
    }

    public Long getSecurityId() {
        return getLong(FixProtocol.FIELD_SECURITY_ID);
    }

    public boolean hasSecurityId() {
        return isSetField(FixProtocol.FIELD_SECURITY_ID);
    }
}
