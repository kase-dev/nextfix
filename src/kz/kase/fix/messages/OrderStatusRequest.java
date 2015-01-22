package kz.kase.fix.messages;


import kz.kase.fix.FixProtocol;
import quickfix.Message;

import java.util.Date;

import static kz.kase.fix.FixProtocol.*;


public class OrderStatusRequest extends Message {
    static final long serialVersionUID = 20050617;

    public OrderStatusRequest() {
        super();
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, MESSAGE_ORDER_STATUS_REQUEST);
    }

    public OrderStatusRequest setRef(long ref) {
        setLong(FIELD_REF_TAG_ID, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FIELD_REF_TAG_ID);
    }

    public OrderStatusRequest setSymbol(String symbol) {
        setString(FIELD_SYMBOL, symbol);
        return this;
    }

    public String getSymbol() {
        return getString(FIELD_SYMBOL);

    }

    public boolean hasSymbol() {
        return isSetField(FIELD_SYMBOL);
    }


    public OrderStatusRequest setFromDate(Date from) {
        setUtcDateOnly(FIELD_ARC_POS_DATE_FROM, from);
        return this;
    }

    public Date getFromDate() {
        return getUtcDateOnly(FIELD_ARC_POS_DATE_FROM);
    }

    public boolean hasFromDate() {
        return isSetField(FIELD_ARC_POS_DATE_FROM);
    }

    public OrderStatusRequest setToDate(Date to) {
        setUtcDateOnly(FIELD_ARC_POS_DATE_TO, to);
        return this;
    }

    public Date getToDate() {
        return getUtcDateOnly(FIELD_ARC_POS_DATE_TO);
    }

    public boolean hasToDate() {
        return isSetField(FIELD_ARC_POS_DATE_TO);
    }


}

