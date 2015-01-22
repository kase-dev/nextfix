package kz.kase.fix.messages;

import kz.kase.fix.FixProtocol;
import quickfix.Message;

public class TestRequest extends Message {

    public TestRequest() {
        super();
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_TEST_REQUEST);
    }

    public TestRequest setTestReqId(String reqId) {
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
