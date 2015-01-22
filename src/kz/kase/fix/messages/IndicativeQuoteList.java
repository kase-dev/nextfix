package kz.kase.fix.messages;

import quickfix.Group;
import quickfix.Message;

import java.util.Date;
import java.util.List;

import static kz.kase.fix.FixProtocol.*;

public class IndicativeQuoteList extends Message {

    public IndicativeQuoteList() {
        super(new int[]{
                FIELD_INDICATIVE_QUOTE_REF
        });

        getHeader().setString(FIELD_MESSAGE_TYPE, MESSAGE_INDICATIVE_QUOTE);
    }

    public IndicativeQuoteList setRef(long ref) {
        setLong(FIELD_INDICATIVE_QUOTE_REF, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FIELD_INDICATIVE_QUOTE_REF);
    }

    public boolean hasRef() {
        return isSetField(FIELD_INDICATIVE_QUOTE_REF);
    }

    public int getQuotesCount() {
        List<Group> grps = getGroups(FIELD_INDICATIVE_QUOTE_ENTRIES);
        return grps != null ? grps.size() : 0;
    }

    public static class IndicativeQuoteEntries extends Group {

        public IndicativeQuoteEntries() {
            super(FIELD_INDICATIVE_QUOTE_ENTRIES, FIELD_INDICATIVE_QUOTE_ID, new int[]{
                    FIELD_INDICATIVE_QUOTE_ID,
                    FIELD_INDICATIVE_QUOTE_INSTR_SYMBOL,
                    FIELD_INDICATIVE_QUOTE_MEMBER_NAME,
                    FIELD_INDICATIVE_QUOTE_BUY_PRICE,
                    FIELD_INDICATIVE_QUOTE_SELL_PRICE,
                    FIELD_INDICATIVE_QUOTE_BUY_VOLUME,
                    FIELD_INDICATIVE_QUOTE_SELL_VOLUME,
                    FIELD_INDICATIVE_QUOTE_STATUS,
                    FIELD_INDICATIVE_QUOTE_CREATION_TIME,
                    FIELD_INDICATIVE_QUOTE_COMMENT
            });
        }

        public IndicativeQuoteEntries setId(long id) {
            setLong(FIELD_INDICATIVE_QUOTE_ID, id);
            return this;
        }

        public Long getId() {
            return getLong(FIELD_INDICATIVE_QUOTE_ID);
        }

        public boolean hasId() {
            return isSetField(FIELD_INDICATIVE_QUOTE_ID);
        }

        public IndicativeQuoteEntries setInstrSymbol(String instrSymbol) {
            setString(FIELD_INDICATIVE_QUOTE_INSTR_SYMBOL, instrSymbol);
            return this;
        }

        public String getInstrSymbol() {
            return getString(FIELD_INDICATIVE_QUOTE_INSTR_SYMBOL);
        }

        public boolean hasInstrSymbol() {
            return isSetField(FIELD_INDICATIVE_QUOTE_INSTR_SYMBOL);
        }

        public IndicativeQuoteEntries setMemberName(String memberName) {
            setString(FIELD_INDICATIVE_QUOTE_MEMBER_NAME, memberName);
            return this;
        }

        public String getMemberName() {
            return getString(FIELD_INDICATIVE_QUOTE_MEMBER_NAME);
        }

        public boolean hasMemberName() {
            return isSetField(FIELD_INDICATIVE_QUOTE_MEMBER_NAME);
        }

        public IndicativeQuoteEntries setBuyPrice(double price) {
            setDouble(FIELD_INDICATIVE_QUOTE_BUY_PRICE, price);
            return this;
        }

        public Double getBuyPrice() {
            return getDouble(FIELD_INDICATIVE_QUOTE_BUY_PRICE);
        }

        public boolean hasBuyPrice() {
            return isSetField(FIELD_INDICATIVE_QUOTE_BUY_PRICE);
        }

        public IndicativeQuoteEntries setSellPrice(double price) {
            setDouble(FIELD_INDICATIVE_QUOTE_SELL_PRICE, price);
            return this;
        }

        public Double getSellPrice() {
            return getDouble(FIELD_INDICATIVE_QUOTE_SELL_PRICE);
        }

        public boolean hasSellPrice() {
            return isSetField(FIELD_INDICATIVE_QUOTE_SELL_PRICE);
        }

        public IndicativeQuoteEntries setBuyVol(long vol) {
            setLong(FIELD_INDICATIVE_QUOTE_BUY_VOLUME, vol);
            return this;
        }

        public Long getBuyVol() {
            return getLong(FIELD_INDICATIVE_QUOTE_BUY_VOLUME);
        }

        public boolean hasBuyVol() {
            return isSetField(FIELD_INDICATIVE_QUOTE_BUY_VOLUME);
        }

        public IndicativeQuoteEntries setSellVol(long vol) {
            setLong(FIELD_INDICATIVE_QUOTE_SELL_VOLUME, vol);
            return this;
        }

        public Long getSellVol() {
            return getLong(FIELD_INDICATIVE_QUOTE_SELL_VOLUME);
        }

        public boolean hasSellVol() {
            return isSetField(FIELD_INDICATIVE_QUOTE_SELL_VOLUME);
        }

        public IndicativeQuoteEntries setStatus(String status) {
            setString(FIELD_INDICATIVE_QUOTE_STATUS, status);
            return this;
        }

        public String getStatus() {
            return getString(FIELD_INDICATIVE_QUOTE_STATUS);
        }

        public boolean hasStatus() {
            return isSetField(FIELD_INDICATIVE_QUOTE_STATUS);
        }

        public IndicativeQuoteEntries setCreationTime(Date time) {
            setUtcTimeStamp(FIELD_INDICATIVE_QUOTE_CREATION_TIME, time);
            return this;
        }

        public Date getCreationTime() {
            return getUtcTimeStamp(FIELD_INDICATIVE_QUOTE_CREATION_TIME);
        }

        public boolean hasCreationTime() {
            return isSetField(FIELD_INDICATIVE_QUOTE_CREATION_TIME);
        }

        public IndicativeQuoteEntries setComment(String comment) {
            setString(FIELD_INDICATIVE_QUOTE_COMMENT, comment);
            return this;
        }

        public String getComment() {
            return getString(FIELD_INDICATIVE_QUOTE_COMMENT);
        }

        public boolean hasComment() {
            return isSetField(FIELD_INDICATIVE_QUOTE_COMMENT);
        }
    }
}
