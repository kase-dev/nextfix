package kz.kase.fix.messages;

import kz.kase.fix.FixProtocol;
import kz.kase.fix.Side;
import quickfix.Message;

import java.util.Date;

import static kz.kase.fix.FixProtocol.*;

public class OrderCancelReplaceRequest extends Message {

    public OrderCancelReplaceRequest() {
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_ORDER_CANCEL_REPLACE_REQUEST);
    }

    public OrderCancelReplaceRequest setRef(long ref) {
        setLong(FIELD_REFERENCE, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FIELD_REFERENCE);
    }

    public boolean hasRef() {
        return isSetField(FIELD_REFERENCE);
    }

    public OrderCancelReplaceRequest setOrderID(String ref) {
        setString(FIELD_ORDER_ID, ref);
        return this;
    }

    public String getOrderID() {
        return getString(FIELD_ORDER_ID);
    }

    public boolean hasOrderID() {
        return isSetField(FIELD_ORDER_ID);
    }

    public OrderCancelReplaceRequest setOrigOrdID(String origOrdID) {
        setString(FIELD_ORIG_CLORDID, origOrdID);
        return this;
    }

    public String getOrigOrdID() {
        return getString(FIELD_ORIG_CLORDID);
    }

    public boolean hasOrigOrdID() {
        return isSetField(FIELD_ORIG_CLORDID);
    }

    public OrderCancelReplaceRequest setPrice(Double price) {
        setDouble(FIELD_PRICE, price);
        return this;
    }

    public Double getPrice() {
        return getDouble(FIELD_PRICE);
    }

    public boolean hasPrice() {
        return isSetField(FIELD_PRICE);
    }

    public OrderCancelReplaceRequest setQty(Long qty) {
        setLong(FIELD_ORDER_QTY, qty);
        return this;
    }

    public Long getQty() {
        return getLong(FIELD_ORDER_QTY);
    }

    public boolean hasQty() {
        return isSetField(FIELD_ORDER_QTY);
    }

}
