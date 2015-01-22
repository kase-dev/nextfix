package kz.kase.fix.messages;

import kz.kase.fix.FixProtocol;
import kz.kase.fix.Product;
import quickfix.Group;
import quickfix.Message;

import java.util.Date;
import java.util.List;

import static kz.kase.fix.FixProtocol.*;

public class DayPositionReport extends Message {

    public DayPositionReport() {
        super(new int[]{
                FIELD_REF_TAG_ID,
                FIELD_ACCOUNT_NAME,
                FIELD_MEMBER_NAME,
                FIELD_PRODUCT
        });
        getHeader().setString(FIELD_MESSAGE_TYPE, MESSAGE_DAY_POS_REPORT);
    }

    public DayPositionReport setMemberName(String name) {
        setString(FIELD_MEMBER_NAME, name);
        return this;
    }

    public String getMemberName() {
        return getString(FIELD_MEMBER_NAME);
    }

    public boolean hasMemberName() {
        return isSetField(FIELD_MEMBER_NAME);
    }

    public DayPositionReport setAccName(String acc) {
        setString(FIELD_ACCOUNT_NAME, acc);
        return this;
    }

    public DayPositionReport setMarketType(Product product) {
        setString(FixProtocol.FIELD_PRODUCT, Integer.toString(product.getValue()));
        return this;
    }

    public Product getMarketType() {
        return Product.valueOf(getInt(FixProtocol.FIELD_PRODUCT));
    }

    public boolean hasMarketType() {
        return isSetField(FixProtocol.FIELD_PRODUCT);
    }

    public DayPositionReport setRef(long ref) {
        setLong(FIELD_REF_TAG_ID, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FIELD_REF_TAG_ID);
    }

    public boolean hasRef() {
        return isSetField(FIELD_REF_TAG_ID);
    }

    public String getAccName() {
        return getString(FIELD_ACCOUNT_NAME);
    }

    public boolean hasAccName() {
        return isSetField(FIELD_ACCOUNT_NAME);
    }

    public int getPositionsCount() {
        List<Group> grps = getGroups(FIELD_DAY_POS_KEY);
        return grps != null ? grps.size() : 0;
    }

    public static class PosDayKey extends Group {

        public PosDayKey() {
            super(FIELD_DAY_POS_KEY,
                    FIELD_CURRENCY_NAME,
                    new int[]{
                            FIELD_CURRENCY_NAME,
                            FIELD_BUY_POS,
                            FIELD_SELL_POS,
                            FIELD_NET_POS,
                            FIELD_BUY_BLOCKED_POS,
                            FIELD_SELL_BLOCKED_POS,
                            FIELD_SETTLEMENT_POS_DATE,
                            FIELD_T_PLUS_N,
                            FIELD_DAY_POS_ID,
                            FIELD_MEMBER_NAME
                    }
            );

        }

        public PosDayKey setId(long id) {
            setLong(FIELD_DAY_POS_ID, id);
            return this;
        }

        public Long getId() {
            return getLong(FIELD_DAY_POS_ID);
        }

        public PosDayKey setSettDate(Date time) {
            setUtcDateOnly(FIELD_SETTLEMENT_POS_DATE, time);
            return this;
        }

        public PosDayKey setCurName(String name) {
            setString(FIELD_CURRENCY_NAME, name);
            return this;
        }

        public Date getSettDate() {
            return getUtcTimeStamp(FIELD_SETTLEMENT_POS_DATE);
        }

        public boolean hasSettDate() {
            return isSetField(FIELD_SETTLEMENT_POS_DATE);
        }

        public PosDayKey setTPlusN(int val) {
            setInt(FIELD_T_PLUS_N, val);
            return this;
        }

        public Double getTPlusN() {
            return getDouble(FIELD_T_PLUS_N);
        }

        public boolean nasTPlusN() {
            return isSetField(FIELD_T_PLUS_N);
        }

        public PosDayKey setBuyPos(double pos) {
            setDouble(FIELD_BUY_POS, pos);
            return this;
        }

        public Double getBuyPos() {
            return getDouble(FIELD_BUY_POS);
        }

        public boolean hasBuyPos() {
            return isSetField(FIELD_BUY_POS);
        }

        public PosDayKey setSellPos(double pos) {
            setDouble(FIELD_SELL_POS, pos);
            return this;
        }

        public Double getSellPos() {
            return getDouble(FIELD_SELL_POS);
        }

        public boolean hasSellPos() {
            return isSetField(FIELD_SELL_POS);
        }

        public PosDayKey setNetPos(double pos) {
            setDouble(FIELD_NET_POS, pos);
            return this;
        }

        public Double getNetPos() {
            return getDouble(FIELD_NET_POS);
        }

        public boolean hasNetPos() {
            return isSetField(FIELD_NET_POS);
        }

        public PosDayKey setBuyBlockedPos(double pos) {
            setDouble(FIELD_BUY_BLOCKED_POS, pos);
            return this;
        }

        public Double getBuyBlockedPos() {
            return getDouble(FIELD_BUY_BLOCKED_POS);
        }

        public boolean hasBuyBlockedPos() {
            return isSetField(FIELD_BUY_BLOCKED_POS);
        }

        public PosDayKey setSellBlockedPos(double pos) {
            setDouble(FIELD_SELL_BLOCKED_POS, pos);
            return this;
        }

        public Double getSellBlockedPos() {
            return getDouble(FIELD_SELL_BLOCKED_POS);
        }

        public boolean hasSellBlockedPos() {
            return isSetField(FIELD_SELL_BLOCKED_POS);
        }

    }
}
