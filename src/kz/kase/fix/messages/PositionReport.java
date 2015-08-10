package kz.kase.fix.messages;

import kz.kase.fix.*;
import quickfix.Group;
import quickfix.Message;

import java.util.Date;

public class PositionReport extends Message {


    private static final int[] order = new int[]{721, 715, 1, 55, 48, 15, 702};

    public PositionReport() {
        super(order);
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_POS_REPORT);
    }

    public PositionReport setRef(long ref) {
        setLong(FixProtocol.FIELD_POS_MAINT_RPT_ID, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FixProtocol.FIELD_POS_MAINT_RPT_ID);
    }

    public PositionReport setAccount(String account) {
        setString(FixProtocol.FIELD_ACCOUNT, account);
        return this;
    }

    public String getAccount() {
        return getString(FixProtocol.FIELD_ACCOUNT);
    }

    public PositionReport setClearingBusinessDate(Date date) {
        setUtcDateOnly(FixProtocol.FIELD_CLEARING_BUSINESS_DATE, date);
        return this;
    }

    public Date getClearingBusinessDate() {
        return getUtcDateOnly(FixProtocol.FIELD_CLEARING_BUSINESS_DATE);
    }

    public PositionReport setSymbol(String symbol) {
        setString(FixProtocol.FIELD_SYMBOL, symbol);
        return this;
    }

    public String getSymbol() {
        return getString(FixProtocol.FIELD_SYMBOL);
    }

    public PositionReport setSecurityId(String securityId) {
        setString(FixProtocol.FIELD_SECURITY_ID, securityId);
        return this;
    }

    public String getSecurityId() {
        return getString(FixProtocol.FIELD_SECURITY_ID);
    }

    public PositionReport setCurrency(String currency) {
        setString(FixProtocol.FIELD_CURRENCY, currency);
        return this;
    }

    public String getCurrency() {
        return getString(FixProtocol.FIELD_CURRENCY);
    }

    public static class PositionQty extends Group {
        public PositionQty() {
            super(FixProtocol.FIELD_NO_POSITIONS, FixProtocol.FIELD_POSITION_TYPE,
                    new int[]{
                        703, 704
                    }
            );
        }

        public PositionQty setPosType(String posType) {
            setString(FixProtocol.FIELD_POSITION_TYPE, posType);
            return this;
        }

        public String getPosType() {
            return getString(FixProtocol.FIELD_POSITION_TYPE);
        }

        public PositionQty setLongQty(Double pos) {
            setDouble(FixProtocol.FIELD_LONG_QTY, pos);
            return this;
        }

        public Double getLongQty() {
            return getDouble(FixProtocol.FIELD_LONG_QTY);
        }

    }
}
