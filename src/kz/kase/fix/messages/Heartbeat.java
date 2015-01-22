package kz.kase.fix.messages;

import kz.kase.fix.FixProtocol;
import quickfix.Message;

public class Heartbeat extends Message {

    public Heartbeat() {
        super();
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_HEARTBEAT);
    }

    public Heartbeat setTestReqId(String reqId) {
        setString(FixProtocol.FIELD_TEST_REQ_ID, reqId);
        return this;
    }

    public String getTestReqId() {
        return getString(FixProtocol.FIELD_TEST_REQ_ID);
    }

    public boolean hasTestReqId() {
        return isSetField(FixProtocol.FIELD_TEST_REQ_ID);
    }

}
