package kz.kase.fix.messages;

import kz.kase.fix.FixProtocol;
import kz.kase.fix.Side;
import quickfix.Message;

import java.util.Date;

import static kz.kase.fix.FixProtocol.*;

public class OrderCancelRequest extends Message {

    public OrderCancelRequest() {
        this(false);
    }

    public OrderCancelRequest(boolean parse) {
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_ORDER_CANCEL_REQUEST);
        if (!parse) {
            setString(FixProtocol.FIELD_SYMBOL, FixProtocol.NOT_SET);
            setChar(FIELD_SIDE, Side.BUY.getValue());
            setUtcTimeStamp(FIELD_TRANSACTION_TIME, new Date());
            setString(FixProtocol.FIELD_ORIG_CLORDID, FixProtocol.NOT_SET);
        }
    }

    public OrderCancelRequest setRef(long ref) {
        setLong(FIELD_REFERENCE, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FIELD_REFERENCE);
    }

    public boolean hasRef() {
        return isSetField(FIELD_REFERENCE);
    }

    public OrderCancelRequest setSymbol(String symbol) {
        setString(FIELD_SYMBOL, symbol);
        return this;
    }

    public String getSymbol() {
        return getString(FIELD_SYMBOL);
    }

    public OrderCancelRequest setOrderQty(int orderQty) {
        setInt(FIELD_ORDER_QTY, orderQty);
        return this;
    }

    public int getOrderQty() {
        return getInt(FIELD_ORDER_QTY);
    }


//    public OrderCancelRequest setOrderId(long orderId) {
//        setLong(FixProtocol.FIELD_ORDER_ID, orderId);
//        return this;
//    }

    public OrderCancelRequest setOrderSerial(String serial) {
        setString(FIELD_ORDER_SERIAL, serial);
        return this;
    }

    public String geOrderSerial() {
        return getString(FIELD_ORDER_SERIAL);
    }

    public boolean hasOrderSerial() {
        return isSetField(FIELD_ORDER_SERIAL);
    }

//    public boolean hasOrderId() {
//        return isSetField(FixProtocol.FIELD_ORDER_ID);
//    }


}
