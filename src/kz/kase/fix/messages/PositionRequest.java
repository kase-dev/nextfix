package kz.kase.fix.messages;

import kz.kase.fix.*;
import quickfix.Message;

import java.util.Date;

import static kz.kase.fix.FixProtocol.*;

public class PositionRequest extends Message {

    private boolean posParsed = false;

    public PositionRequest() {
        this(false);
    }

    public PositionRequest(boolean parse) {
        super();
        getHeader().setString(FIELD_MESSAGE_TYPE, MESSAGE_POS_REQUEST);
        if (!parse) {
            PosTransType transType = PosTransType.EXERCISE;
            PosMaintAction maintAction = PosMaintAction.NEW;

            setInt(FIELD_POSITION_TRANS_TYPE, transType.getValue());
            setInt(FIELD_POSITION_MAINT_ACTION, maintAction.getValue());
            setUtcDateOnly(FIELD_CLEARING_BUSINESS_DATE, new Date());
            setString(FIELD_ACCOUNT, FixProtocol.NOT_SET);
            setInt(FIELD_ACCOUNT_TYPE, AccountType.HOUSE_TRADER.getValue());
            setString(FIELD_SYMBOL, FixProtocol.NOT_SET);
            setUtcTimeStamp(FIELD_TRANSACTION_TIME, new Date());

            addGroup(new NoPositions().setPosType(PositionType.AllocationTradeQty));
        }

    }

    public PositionRequest setRef(long ref) {
        setLong(FIELD_POSITION_REQ_ID, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FIELD_POSITION_REQ_ID);
    }

    public boolean hasRef() {
        return isSetField(FIELD_POSITION_REQ_ID);
    }

}
