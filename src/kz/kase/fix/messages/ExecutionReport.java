package kz.kase.fix.messages;

import kz.kase.fix.*;
import quickfix.Message;

import java.util.Date;

import static kz.kase.fix.FixProtocol.*;

public class ExecutionReport extends Message {

    public ExecutionReport() {
        this(false);
    }

    public ExecutionReport(boolean parse) {
        super();
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_EXEC_REPORT);
        if (!parse) {
            setString(FixProtocol.FIELD_EXEC_ID, FixProtocol.NOT_SET);
            setDouble(FixProtocol.FIELD_AVG_PRC, 0);
        }
    }

    public ExecutionReport setRef(long ref) {
        setLong(FixProtocol.FIELD_REFERENCE, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FixProtocol.FIELD_REFERENCE);
    }

    public boolean hasRef() {
        return isSetField(FixProtocol.FIELD_REFERENCE);
    }


    public ExecutionReport setExecType(ExecType execType) {
        setChar(FixProtocol.FIELD_EXEC_TYPE, execType.getValue());
        return this;
    }

    public ExecutionReport setLastQty(int qty) {
        setInt(FIELD_LAST_QTY, qty);
        return this;
    }

    public Integer getLastQty() {
        return getInt(FIELD_LAST_QTY);
    }

    public boolean hasLastQty() {
        return isSetField(FIELD_LAST_QTY);
    }

    public ExecutionReport setLastPrice(double price) {
        setDouble(FIELD_LAST_PX, price);
        return this;
    }

    public Double getLastPrice() {
        return getDouble(FIELD_LAST_PX);
    }

    public boolean hasLastPrice() {
        return isSetField(FIELD_LAST_PX);
    }

    public ExecType getExecType() {
        return ExecType.valueOf(getChar(FixProtocol.FIELD_EXEC_TYPE));
    }

    public boolean hasExecType() {
        return isSetField(FixProtocol.FIELD_EXEC_TYPE);
    }

    public ExecutionReport setExecId(String execId) {
        setString(FixProtocol.FIELD_EXEC_ID, execId);
        return this;
    }

    public String getExecId() {
        return getString(FixProtocol.FIELD_EXEC_ID);
    }

    public boolean hasExecId() {
        return isSetField(FixProtocol.FIELD_EXEC_ID);
    }

    public ExecutionReport setUserName(String name) {
        setString(FIELD_USERNAME, name);
        return this;
    }

    public String getUserName() {
        return getString(FIELD_USERNAME);
    }

    public boolean hasUserName() {
        return isSetField(FIELD_USERNAME);
    }

    public boolean hasTradeSessionId() {
        return isSetField(FixProtocol.FIELD_TRADE_SESSION_ID);
    }

    public ExecutionReport setTradeSessionId(String id) {
        setString(FixProtocol.FIELD_TRADE_SESSION_ID, id);
        return this;
    }

    public String getSessionId() {
        return getString(FixProtocol.FIELD_TRADE_SESSION_ID);
    }

    public boolean hasTradeSessionSubId() {
        return isSetField(FixProtocol.FIELD_TRADE_SESSION_SUB_ID);
    }

    public ExecutionReport setTradeSessionSubId(String subId) {
        setString(FixProtocol.FIELD_TRADE_SESSION_SUB_ID, subId);
        return this;
    }

    public String getTradeSessionSubId() {
        return getString(FixProtocol.FIELD_TRADE_SESSION_SUB_ID);
    }

    public ExecutionReport setInstrSymbol(String symbol) {
        setString(FIELD_SYMBOL, symbol);
        return this;
    }

    public String getInstrSymbol() {
        return getString(FIELD_SYMBOL);
    }

    public boolean hasInstrSymbol() {
        return isSetField(FIELD_SYMBOL);
    }


    /*   public ExecutionReport setSecurityId(long secId) {
           setLong(FixProtocol.FIELD_SECURITY_ID, secId);
           return this;
       }

       public Long getSecurityId() {
           return getLong(FixProtocol.FIELD_SECURITY_ID);
       }

       public boolean hasSecurityId() {
           return isSetField(FixProtocol.FIELD_SECURITY_ID);
       }
   */
    public ExecutionReport setAccountId(String accId) {
        setString(FixProtocol.FIELD_ACCOUNT, accId);
        return this;
    }

    public String getAccountId() {
        return getString(FixProtocol.FIELD_ACCOUNT);
    }

    public boolean hasAccountId() {
        return isSetField(FixProtocol.FIELD_ACCOUNT);
    }

    public ExecutionReport setAccOrdName(String name) {
        setString(FIELD_ACCOUNT_NAME, name);
        return this;
    }

    public String getAccOrdName() {
        return getString(FIELD_ACCOUNT_NAME);
    }

    public boolean hasAccOrdName() {
        return isSetField(FIELD_ACCOUNT_NAME);
    }

   /* public Long getAccountId() {
        return getLong(FixProtocol.FIELD_ACCOUNT);
    }

    public boolean hasAccount() {
        return isSetField(FixProtocol.FIELD_ACCOUNT);
    }*/

    public Date getExpireDate() {
        return getUtcDateOnly(FixProtocol.FIELD_EXPIRE_DATE);

    }

    public ExecutionReport setExpireDate(Date date) {
        setUtcDateOnly(FixProtocol.FIELD_EXPIRE_DATE, date);
        return this;
    }

    public boolean hasExpireDate() {
        return isSetField(FixProtocol.FIELD_EXPIRE_DATE);
    }

    public ExecutionReport setOrderStatus(OrderStatus status) {
        setChar(FixProtocol.FIELD_ORDER_STATUS, status.getValue());
        return this;
    }

    public OrderStatus getOrderStatus() {
        return OrderStatus.valueOf(getChar(FixProtocol.FIELD_ORDER_STATUS));
    }

    public boolean hasOrderStatus() {
        return isSetField(FixProtocol.FIELD_ORDER_STATUS);
    }

    public ExecutionReport setSide(Side side) {
        setChar(FixProtocol.FIELD_SIDE, side.getValue());
        return this;
    }

    public Side getSide() {
        return Side.valueOf(getChar(FixProtocol.FIELD_SIDE));
    }

    public boolean hasSide() {
        return isSetField(FixProtocol.FIELD_SIDE);
    }

    public ExecutionReport setOrderType(OrderType type) {
        setChar(FixProtocol.FIELD_ORDER_TYPE, type.getValue());
        return this;
    }

    public OrderType getOrderType() {
        return OrderType.valueOf(getChar(FixProtocol.FIELD_ORDER_TYPE));
    }

    public ExecutionReport setPrice(double price) {
        setDouble(FixProtocol.FIELD_PRICE, price);
        return this;
    }

    public Double getPrice() {
        return getDouble(FixProtocol.FIELD_PRICE);
    }

    public boolean hasPrice() {
        return isSetField(FixProtocol.FIELD_PRICE);
    }

    public ExecutionReport setQty(long qty) {
        setLong(FixProtocol.FIELD_ORDER_QTY, qty);
        return this;
    }

    public boolean hasOrderCashQty() {
        return isSetField(FIELD_CASH_QTY);
    }

    public ExecutionReport setOrderCashQty(Double qty) {
        setDouble(FIELD_CASH_QTY, qty);
        return this;
    }

    public Double getOrderCashQty() {
        return getDouble(FIELD_CASH_QTY);
    }

    public boolean hasOrderRestrictions() {
        return isSetField(FIELD_ORDER_RESTRICTIONS);
    }

    public ExecutionReport setOrderRestrictions(String orderRestriction) {
        setString(FIELD_ORDER_RESTRICTIONS, orderRestriction);
        return this;
    }

    public String getOrderRestrictions() {
        return getString(FIELD_ORDER_RESTRICTIONS);
    }


    public ExecutionReport setYield(Double yield) {
        setDouble(FILED_YIELD, yield);
        return this;
    }

    public Double getYield() {
        return getDouble(FILED_YIELD);
    }

    public Long getQty() {
        return getLong(FixProtocol.FIELD_ORDER_QTY);
    }

    public boolean hasQty() {
        return isSetField(FixProtocol.FIELD_ORDER_QTY);
    }

    public ExecutionReport setLeavesQty(long qty) {
        setLong(FixProtocol.FIELD_LEAVES_QTY, qty);
        return this;
    }

    public Long getLeavesQty() {
        return getLong(FixProtocol.FIELD_LEAVES_QTY);
    }

    public boolean hasLeavesQty() {
        return isSetField(FixProtocol.FIELD_LEAVES_QTY);
    }

    public ExecutionReport setCumQty(long qty) {
        setLong(FixProtocol.FIELD_CUM_QTY, qty);
        return this;
    }

    public Long getCumQty() {
        return getLong(FixProtocol.FIELD_CUM_QTY);
    }

    public boolean hasCumQty() {
        return isSetField(FixProtocol.FIELD_CUM_QTY);
    }

    public Long getMaxFloor() {
        return getLong(FixProtocol.FIELD_MAX_FLOOR);
    }

    public boolean hasMaxFloor() {
        return isSetField(FixProtocol.FIELD_MAX_FLOOR);
    }

    public ExecutionReport setMaxFloor(long qty) {
        setLong(FixProtocol.FIELD_MAX_FLOOR, qty);
        return this;
    }


    public ExecutionReport setOrdRejReason(OrdRejReason reason) {
        setInt(FixProtocol.FIELD_ORDER_REJECT_REASON, reason.getValue());
        return this;
    }

    public OrdRejReason getOrdRejReason() {
        return OrdRejReason.valueOf(getInt(FixProtocol.FIELD_ORDER_REJECT_REASON));
    }

    public boolean hasOrdRejReason() {
        return isSetField(FixProtocol.FIELD_ORDER_REJECT_REASON);
    }


    public ExecutionReport setTransactionTime(Date time) {
        setUtcTimeStamp(FIELD_TRANSACTION_TIME, time);
        return this;
    }

    public Date getTransactionTime() {
        return getUtcTimeStamp(FixProtocol.FIELD_TRANSACTION_TIME);
    }

    public boolean hasTransactionTime() {
        return isSetField(FixProtocol.FIELD_TRANSACTION_TIME);
    }

    public ExecutionReport setEffectiveTime(Date time) {
        setUtcTimeStamp(FIELD_EFFECTIVE_TIME, time);
        return this;
    }

    public boolean hasEffectiveTime() {
        return isSetField(FIELD_EFFECTIVE_TIME);
    }

    public Date getEffectiveTime() {
        return getUtcTimeStamp(FIELD_EFFECTIVE_TIME);
    }


    public String getComment() {
        return getString(FixProtocol.FIELD_TEXT);
    }

    public boolean hasComment() {
        return isSetField(FixProtocol.FIELD_TEXT);
    }

    public ExecutionReport setComment(String comment) {
        setString(FixProtocol.FIELD_TEXT, comment);
        return this;
    }

    public ExecutionReport setRemovedTime(Date date) {
        setUtcTimeStamp(FIELD_REMOVED_TIME, date);
        return this;

    }

    public ExecutionReport setSellUserName(String name) {
        setString(FIELD_SELL_USER_NAME, name);
        return this;
    }

    public String getSellUserName() {
        return getString(FIELD_SELL_USER_NAME);
    }

    public boolean hasSellUserName() {
        return isSetField(FIELD_SELL_USER_NAME);
    }

    public ExecutionReport setBuyAcc(String acc) {
        setString(FIELD_BUY_ACC, acc);
        return this;
    }

    public String getBuyAcc() {
        return getString(FIELD_BUY_ACC);
    }

    public boolean hasBuyAcc() {
        return isSetField(FIELD_BUY_ACC);
    }

    public ExecutionReport setSellAcc(String acc) {
        setString(FIELD_SELL_ACC, acc);
        return this;
    }

    public String getSellAcc() {
        return getString(FIELD_SELL_ACC);
    }

    public boolean hasSellAcc() {
        return isSetField(FIELD_SELL_ACC);
    }

    public ExecutionReport setOrderId(String serial) {
        setString(FIELD_ORDER_ID, serial);
        return this;
    }

    public String getOrderId() {
        return getString(FIELD_ORDER_ID);
    }

    public boolean hasOrderId() {
        return isSetField(FIELD_ORDER_ID);
    }

    public ExecutionReport setSellOrderSerial(String serial) {
        setString(FIELD_SELL_ORDER_SERIAL, serial);
        return this;
    }

    public String getSellOrderSerial() {
        return getString(FIELD_SELL_ORDER_SERIAL);
    }

    public boolean hasSellOrderSerial() {
        return isSetField(FIELD_SELL_ORDER_SERIAL);
    }

    public ExecutionReport setMemberName(String name) {
        setString(FIELD_MEMBER_NAME, name);
        return this;
    }

    public String getMemberName() {
        return getString(FIELD_MEMBER_NAME);
    }

    public boolean hasMemberName() {
        return isSetField(FIELD_MEMBER_NAME);
    }

    public Date getRemovedTime() {
        return getUtcTimeStamp(FIELD_REMOVED_TIME);
    }

    public boolean hasRemovedTime() {
        return isSetField(FIELD_REMOVED_TIME);
    }

    public boolean getMMType() {
        return getBoolean(FIELD_MM_TYPE);
    }

    public boolean hasMMType() {
        return isSetField(FIELD_MM_TYPE);
    }

    public ExecutionReport setTimeInForce(TimeInForce tif) {
        setChar(FIELD_TIME_IN_FORCE, tif.getValue());
        return this;
    }

    public Character getTimeInForce() {
        return getChar(FIELD_TIME_IN_FORCE);
    }

    public boolean hasTimeInForce() {
        return isSetField(FIELD_TIME_IN_FORCE);
    }

    public ExecutionReport setWhoRemoved(String name) {
        setString(FIELD_WHO_REMOVED, name);
        return this;
    }

    public String getWhoRemoved() {
        return getString(FIELD_WHO_REMOVED);
    }

    public boolean hasWhoRemoved() {
        return isSetField(FIELD_WHO_REMOVED);
    }

    public ExecutionReport setDealType(String type) {
        setString(FIELD_DEAL_TYPE, type);
        return this;
    }

    public String getDealType() {
        return getString(FIELD_DEAL_TYPE);
    }

    public boolean hasDealType() {
        return isSetField(FIELD_DEAL_TYPE);
    }

}
