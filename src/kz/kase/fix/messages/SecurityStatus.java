package kz.kase.fix.messages;

import kz.kase.fix.FixProtocol;
import kz.kase.fix.SecurityTradingStatus;
import kz.kase.fix.TradeCondition;
import quickfix.Message;

public class SecurityStatus extends Message {

    private static final int[] order = new int[]{55, 48, 461, 326, 336, 625};

    public SecurityStatus() {
        super(order);
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_SEC_STAT);
    }


    public SecurityStatus setSecurityTradingStatus(SecurityTradingStatus st) {
        setInt(FixProtocol.FIELD_SECURITY_TRADE_STATUS, st.getValue());
        return this;
    }

    public boolean hasSecurityTradeStatus() {
        return isSetField(FixProtocol.FIELD_SECURITY_TRADE_STATUS);
    }

    public SecurityTradingStatus getSecurityTradingStatus() {
        int i = getInt(FixProtocol.FIELD_SECURITY_TRADE_STATUS);
        return SecurityTradingStatus.getByValue(i);
    }


    public SecurityStatus setSymbol(String symbol) {
        setString(FixProtocol.FIELD_SYMBOL, symbol);
        return this;
    }

    public boolean hasSymbol() {
        return isSetField(FixProtocol.FIELD_SYMBOL);
    }

    public SecurityStatus setSecurityId(String secId) {
        setString(FixProtocol.FIELD_SECURITY_ID, secId);
        return this;
    }

    public boolean hasSecurityId() {
        return isSetField(FixProtocol.FIELD_SECURITY_ID);
    }

    public boolean hasCFICode() {
        return isSetField(FixProtocol.FIELD_CFI_CODE);
    }

    public SecurityStatus setCFICode(String cfi) {
        setString(FixProtocol.FIELD_CFI_CODE, cfi);
        return this;
    }

    public SecurityStatus setTradingSessionID(String tradingSessionID) {
        setString(FixProtocol.FIELD_TRADE_SESSION_ID, tradingSessionID);
        return this;
    }

    public boolean hasTradingSessionSubId() {
        return isSetField(FixProtocol.FIELD_TRADE_SESSION_SUB_ID);
    }

    public SecurityStatus setTradingSessionSubID(String tradingSessionSubID) {
        setString(FixProtocol.FIELD_TRADE_SESSION_SUB_ID, tradingSessionSubID);
        return this;
    }

    public SecurityStatus setTradingSessionSubID(TradeCondition tc) {
        setString(FixProtocol.FIELD_TRADE_SESSION_SUB_ID, tc.getValue());
        return this;
    }


    public String getSymbol() {
        return getString(FixProtocol.FIELD_SYMBOL);
    }

    public String getSecurityId() {
        return getString(FixProtocol.FIELD_SECURITY_ID);
    }

    public String getCFICode() {
        return getString(FixProtocol.FIELD_CFI_CODE);
    }

    public String getTradingSessionID() {
        return getString(FixProtocol.FIELD_TRADE_SESSION_ID);
    }

    public String getTradingSessionSubID() {
        return getString(FixProtocol.FIELD_TRADE_SESSION_SUB_ID);
    }

    public boolean hasTradingSessionId(){
        return isSetField(FixProtocol.FIELD_TRADE_SESSION_ID);
    }

    public TradeCondition getTrSesSubID() {
        String subId = getString(FixProtocol.FIELD_TRADE_SESSION_SUB_ID);
        if (subId != null) {
            try {
                return TradeCondition.getByValue(subId);
            } catch (IllegalArgumentException ie) {
                return null;
            }
        }
        return null;
    }
}
