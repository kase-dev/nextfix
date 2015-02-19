package kz.kase.fix.messages;

import kz.kase.fix.FixProtocol;
import kz.kase.fix.OrderType;
import kz.kase.fix.Side;
import kz.kase.fix.TimeInForce;
import quickfix.Group;
import quickfix.Message;
import quickfix.field.StringField;

import java.util.Date;

import static kz.kase.fix.FixProtocol.*;


public class NewOrderSingle extends Message {

    public NewOrderSingle() {
        this(false);
    }

    public NewOrderSingle(boolean parse) {
        super();
        getHeader().setString(FIELD_MESSAGE_TYPE, MESSAGE_NEW_ORDER_SINGLE);
        if (!parse) {
            setUtcTimeStamp(FIELD_TRANSACTION_TIME, new Date());
            setSymbol(FixProtocol.NOT_SET);
        }
    }

    public NewOrderSingle setRef(long ref) {
        setField(new StringField(FIELD_REFERENCE, Long.toString(ref)));
        return this;
    }

    public Long getRef() {
        return getLong(FIELD_REFERENCE);
    }

    public boolean hasRef() {
        return isSetField(FIELD_REFERENCE);
    }

    public boolean hasOrderRestrictions() {
        return isSetField(FIELD_ORDER_RESTRICTIONS);
    }

    public NewOrderSingle setOrderRestrictions(String orderRestriction) {
        setString(FIELD_ORDER_RESTRICTIONS, orderRestriction);
        return this;
    }

    public String getOrderRestrictions() {
        return getString(FIELD_ORDER_RESTRICTIONS);
    }

/*
    public NewOrderSingle setAccountId(long accountId) {
        setLong(FIELD_ACCOUNT, accountId);
        return this;
    }

    public Long getAccountId() {
        return getLong(FIELD_ACCOUNT);
    }

    public boolean hasAccountId() {
        return isSetField(FIELD_ACCOUNT);
    }

*/

    public NewOrderSingle setOrderType(OrderType type) {
        setChar(FIELD_ORDER_TYPE, type.getValue());
        return this;
    }

    public OrderType getOrderType() {
        return OrderType.valueOf(getChar(FIELD_ORDER_TYPE));
    }

    public boolean hasOrderType() {
        return isSetField(FIELD_ORDER_TYPE);
    }

    public NewOrderSingle setSymbol(String symbol) {
        setString(FIELD_SYMBOL, symbol);
        return this;
    }

    public String getSymbol() {
        return getString(FIELD_SYMBOL);
    }

    public boolean hasSymbol() {
        return isSetField(FIELD_SYMBOL);
    }

/*
    public NewOrderSingle setSecurityId(long secId) {
        setLong(FIELD_SECURITY_ID, secId);
        return this;
    }

    public Long getSecurityId() {
        return getLong(FIELD_SECURITY_ID);
    }

    public boolean hasSecurityId() {
        return isSetField(FIELD_SECURITY_ID);
    }

*/

    public NewOrderSingle setPrice(double price) {
        setDouble(FIELD_PRICE, price);
        return this;
    }

    public NewOrderSingle setAccount(String acc) {
        setString(FIELD_ACCOUNT, acc);
        return this;
    }

    public String getAccount() {
        return getString(FIELD_ACCOUNT);
    }

    public boolean hasAccount() {
        return isSetField(FIELD_ACCOUNT);
    }

    public Double getPrice() {
        return getDouble(FIELD_PRICE);
    }

    public boolean hasPrice() {
        return isSetField(FIELD_PRICE);
    }

    public NewOrderSingle setStopPrice(double price) {
        setDouble(FIELD_STOP_PRICE, price);
        return this;
    }

    public Double getStopPrice() {
        return getDouble(FIELD_STOP_PRICE);
    }

    public boolean hasStopPrice() {
        return isSetField(FIELD_STOP_PRICE);
    }


    public NewOrderSingle setQty(long symbol) {
        setDouble(FIELD_ORDER_QTY, symbol);
        return this;
    }

    public Long getQty() {
        return getLong(FIELD_ORDER_QTY);
    }

    public boolean hasQty() {
        return isSetField(FIELD_ORDER_QTY);
    }

    public NewOrderSingle setSide(Side side) {
        setChar(FIELD_SIDE, side.getValue());
        return this;
    }

    public Side getSide() {
        return Side.valueOf(getChar(FIELD_SIDE));
    }

    public boolean hasSide() {
        return isSetField(FIELD_SIDE);
    }

    public NewOrderSingle setTimeInForce(TimeInForce tif) {
        setChar(FIELD_TIME_IN_FORCE, tif.getValue());
        return this;
    }

    public TimeInForce getTimeInForce() {
        return TimeInForce.valueOf(getChar(FIELD_TIME_IN_FORCE));
    }

    public boolean hasTimeInForce() {
        return isSetField(FIELD_TIME_IN_FORCE);
    }

    public Long getMaxFloor() {
        return getLong(FixProtocol.FIELD_MAX_FLOOR);
    }

    public boolean hasMaxFloor() {
        return isSetField(FixProtocol.FIELD_MAX_FLOOR);
    }

    public NewOrderSingle setMaxFloor(long qty) {
        setLong(FixProtocol.FIELD_MAX_FLOOR, qty);
        return this;
    }

    public Date getExpireDate() {
        return getUtcDateOnly(FixProtocol.FIELD_EXPIRE_DATE);
    }

    public boolean hasExpireDate() {
        return isSetField(FixProtocol.FIELD_EXPIRE_DATE);
    }

    public NewOrderSingle setExpireDate(Date date) {
//        setUtcTimeStamp(FixProtocol.FIELD_EXPIRE_DATE, date);
        setUtcDateOnly(FixProtocol.FIELD_EXPIRE_DATE, date);
        return this;
    }


    public String getComment() {
        return getString(FixProtocol.FIELD_TEXT);
    }

    public boolean hasComment() {
        return isSetField(FixProtocol.FIELD_TEXT);
    }

    public NewOrderSingle setComment(String comment) {
        if (comment != null && !comment.isEmpty()) {
            setString(FixProtocol.FIELD_TEXT, comment);
        }
        return this;
    }

    public NewOrderSingle setMarketMakerType(boolean flag) {
        setBoolean(FIELD_MM_TYPE, flag);
        return this;
    }

    public Boolean isMarketMakerType() {
        return getBoolean(FIELD_MM_TYPE);
    }

    public boolean hasMarketMakerType() {
        return isSetField(FIELD_MM_TYPE);
    }

    public NewOrderSingle setSpread(double spread) {
        setDouble(FILED_SPREAD, spread);
        return this;
    }

    public NewOrderSingle setPriceType(int priceType) {
        setInt(FIELD_PRICE_TYPE, priceType);
        return this;
    }

    public Integer getPriceType() {
        return getInt(FIELD_PRICE_TYPE);
    }

    public boolean hasPriceType() {
        return isSetField(FIELD_PRICE_TYPE);
    }

    public NewOrderSingle setDoubleOrderType(boolean flag) {
        setBoolean(FIELD_DOUBLE_ORDER, flag);
        return this;
    }

    public boolean isDoubleOrderType() {
        return getBoolean(FIELD_DOUBLE_ORDER);
    }

    public boolean hasDoubleOrderType() {
        return isSetField(FIELD_DOUBLE_ORDER);
    }

    public Double getSpread() {
        return getDouble(FILED_SPREAD);
    }

    public boolean hasSpread() {
        return isSetField(FILED_SPREAD);
    }

    public static class NoPartyIDs extends Group {

        public NoPartyIDs() {
            super(FIELD_NO_PARTY_IDS, FIELD_PARTY_ID,
                    new int[]{
                            448
                    });
        }

        public NoPartyIDs setPartyId(String id) {
            setString(FixProtocol.FIELD_PARTY_ID, id);
            return this;
        }

        public String getPartyId() {
            return getString(FixProtocol.FIELD_PARTY_ID);
        }

    }

    public static class NoStipulations extends Group {

        public NoStipulations() {
            super(FIELD_NO_STIPULATIONS, FIELD_STIPULATION_TYPE,
                    new int[]{
                            233, 234
                    });
        }

        public NoStipulations setStipulationType(String type) {
            setString(FixProtocol.FIELD_STIPULATION_TYPE, type);
            return this;
        }

        public NoStipulations setStipulationValue(double value) {
            setDouble(FixProtocol.FIELD_STIPULATION_VALUE, value);
            return this;
        }

        public String getStipulationType() {
            return getString(FixProtocol.FIELD_STIPULATION_TYPE);
        }

        public String getStipulationValue() {
            return getString(FixProtocol.FIELD_STIPULATION_VALUE);
        }

    }

}
