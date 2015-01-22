package kz.kase.fix.messages;

import kz.kase.fix.FixProtocol;
import quickfix.Message;

public class ResendRequest extends Message {


    public ResendRequest() {
        this(false);
    }

    public ResendRequest(boolean parse) {
        super();
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_RESEND_REQUEST);
    }


    public ResendRequest setBeginSeqNum(int seqNum) {
        setInt(FixProtocol.FIELD_BEGIN_SEQ_NO, seqNum);
        return this;
    }

    public Integer getBeginSeqNum() {
        return getInt(FixProtocol.FIELD_BEGIN_SEQ_NO);
    }

    public boolean hasBeginSeqNum() {
        return isSetField(FixProtocol.FIELD_BEGIN_SEQ_NO);
    }

    public ResendRequest setEndSeqNum(int seqNum) {
        setInt(FixProtocol.FIELD_END_SEQ_NO, seqNum);
        return this;
    }

    public Integer getEndSeqNum() {
        return getInt(FixProtocol.FIELD_END_SEQ_NO);
    }

    public boolean hasEndSeqNum() {
        return isSetField(FixProtocol.FIELD_END_SEQ_NO);
    }


}
