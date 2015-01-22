package kz.kase.fix.messages;

import kz.kase.fix.FixProtocol;
import quickfix.Message;

public class Reject extends Message {


    public Reject() {
        this(false);
    }

    public Reject(boolean parse) {
        super();
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_REJECT);
    }


    public Reject setRefSeqNum(int seqNum) {
        setInt(FixProtocol.FIELD_REF_SEQ_NUM, seqNum);
        return this;
    }

    public Integer getRefSeqNum() {
        return getInt(FixProtocol.FIELD_REF_SEQ_NUM);
    }

    public boolean hasRefSeqNum() {
        return isSetField(FixProtocol.FIELD_REF_SEQ_NUM);
    }

    //todo refTagId
    //todo refMsgType


}
