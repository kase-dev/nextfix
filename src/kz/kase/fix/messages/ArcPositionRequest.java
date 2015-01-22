package kz.kase.fix.messages;

import quickfix.Message;

import java.util.Date;

import static kz.kase.fix.FixProtocol.*;

public class ArcPositionRequest extends Message {

    public ArcPositionRequest() {
        getHeader().setString(FIELD_MESSAGE_TYPE, MESSAGE_ARC_POS_REQUEST);
    }

    public ArcPositionRequest setRef(long ref) {
        setLong(FIELD_REF_TAG_ID, ref);
        return this;
    }

    public ArcPositionRequest setFromDate(Date from) {
        setUtcDateOnly(FIELD_ARC_POS_DATE_FROM, from);
        return this;
    }

    public ArcPositionRequest setToDate(Date to) {
        setUtcDateOnly(FIELD_ARC_POS_DATE_TO, to);
        return this;
    }

}
