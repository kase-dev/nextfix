package kz.kase.fix.messages;

import quickfix.Group;
import quickfix.Message;

import java.util.Date;
import java.util.List;

import static kz.kase.fix.FixProtocol.*;

public class MMStat extends Message {

    public MMStat() {
        super(new int[]{
                FIELD_MM_REQUEST_REF,
                FIELD_SYMBOL,
//                FIELD_MM_LIABILITY_ID,
                FIELD_MM_BUY_PRICE,
                FIELD_MM_BUY_QTY,
                FIELD_MM_BUY_VOL,
                FIELD_MM_SELL_PRICE,
                FIELD_MM_SELL_QTY,
                FIELD_MM_SELL_VOL,
                FIELD_MM_SPREAD,
                FIELD_MM_SPREAD_PERC

        });

        getHeader().setString(FIELD_MESSAGE_TYPE, MESSAGE_MM_STAT);
    }

    public MMStat setRef(long ref) {
        setLong(FIELD_MM_REQUEST_REF, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FIELD_MM_REQUEST_REF);
    }

    public boolean hasRef() {
        return isSetField(FIELD_MM_REQUEST_REF);
    }

    public MMStat setInstrSymbol(String symbol) {
        setString(FIELD_SYMBOL, symbol);
        return this;
    }

    public String getInstrSymbol() {
        return getString(FIELD_SYMBOL);
    }

    public boolean hasInstrSymbol() {
        return isSetField(FIELD_SYMBOL);
    }

    public MMStat setBuyPrice(double price) {
        setDouble(FIELD_MM_BUY_PRICE, price);
        return this;
    }

    public Double getBuyPrice() {
        return getDouble(FIELD_MM_BUY_PRICE);
    }

    public boolean hasBuyPrice() {
        return isSetField(FIELD_MM_BUY_PRICE);
    }

    public MMStat setBuyQty(long qty) {
        setLong(FIELD_MM_BUY_QTY, qty);
        return this;
    }

    public Long getBuyQty() {
        return getLong(FIELD_MM_BUY_QTY);
    }

    public boolean hasBuyQty() {
        return isSetField(FIELD_MM_BUY_QTY);
    }

    public MMStat setBuyVol(double vol) {
        setDouble(FIELD_MM_BUY_VOL, vol);
        return this;
    }

    public Double getBuyVol() {
        return getDouble(FIELD_MM_BUY_VOL);
    }

    public boolean hasBuyVol() {
        return isSetField(FIELD_MM_BUY_VOL);
    }

    public MMStat setSellPrice(double price) {
        setDouble(FIELD_MM_SELL_PRICE, price);
        return this;
    }

    public Double getSellPrice() {
        return getDouble(FIELD_MM_SELL_PRICE);
    }

    public boolean hasSellPrice() {
        return isSetField(FIELD_MM_SELL_PRICE);
    }

    public MMStat setSellQty(long qty) {
        setLong(FIELD_MM_SELL_QTY, qty);
        return this;
    }

    public Long getSellQty() {
        return getLong(FIELD_MM_SELL_QTY);
    }

    public boolean hasSellQty() {
        return isSetField(FIELD_MM_SELL_QTY);
    }

    public MMStat setSellVol(double vol) {
        setDouble(FIELD_MM_SELL_VOL, vol);
        return this;
    }

    public Double getSellVol() {
        return getDouble(FIELD_MM_SELL_VOL);
    }

    public boolean hasSellVol() {
        return isSetField(FIELD_MM_SELL_VOL);
    }

    public MMStat setSpread(double spread) {
        setDouble(FIELD_MM_SPREAD, spread);
        return this;
    }

    public Double getSpread() {
        return getDouble(FIELD_MM_SPREAD);
    }

    public boolean hasSpread() {
        return isSetField(FIELD_MM_SPREAD);
    }

    public MMStat setSpreadPerc(double spread) {
        setDouble(FIELD_MM_SPREAD_PERC, spread);
        return this;
    }

    public Double getSpreadPerc() {
        return getDouble(FIELD_MM_SPREAD_PERC);
    }

    public boolean hasSpreadPesrc() {
        return isSetField(FIELD_MM_SPREAD_PERC);
    }

    public int getWarningsCount() {
        List<Group> grps = getGroups(FIELD_MM_WARNING_ENTRIES);
        return grps != null ? grps.size() : 0;
    }


    public static class MMWarningEntries extends Group {

        public MMWarningEntries() {
            super(FIELD_MM_WARNING_ENTRIES, FIELD_MM_WARN_START_TIME, new int[]{
                    FIELD_SYMBOL,
                    FIELD_MM_WARN_TYPE,
                    FIELD_MM_WARN_START_TIME,
                    FIELD_MM_WARN_END_TIME,
                    FIELD_MM_WARN_CLOSE
            });
        }

        public MMWarningEntries setSymbol(String symbol) {
            setString(FIELD_SYMBOL, symbol);
            return this;
        }

        public String getSymbol() {
            return getString(FIELD_SYMBOL);
        }

        public boolean hasSymbol() {
            return isSetField(FIELD_SYMBOL);
        }

        public MMWarningEntries setStartTime(Date time) {
            setUtcTimeStamp(FIELD_MM_WARN_START_TIME, time);
            return this;
        }

        public Date getStartTime() {
            return getUtcTimeStamp(FIELD_MM_WARN_START_TIME);
        }

        public boolean hasStartTime() {
            return isSetField(FIELD_MM_WARN_START_TIME);
        }

        public MMWarningEntries setEndTime(Date time) {
            setUtcTimeStamp(FIELD_MM_WARN_END_TIME, time);
            return this;
        }

        public Date getEndTime() {
            return getUtcTimeStamp(FIELD_MM_WARN_END_TIME);
        }

        public boolean hasEndTime() {
            return isSetField(FIELD_MM_WARN_END_TIME);
        }

        public MMWarningEntries setType(String type) {
            setString(FIELD_MM_WARN_TYPE, type);
            return this;
        }

        public String getType() {
            return getString(FIELD_MM_WARN_TYPE);
        }

        public boolean hasType() {
            return isSetField(FIELD_MM_WARN_TYPE);
        }

        public MMWarningEntries setClose(boolean flag) {
            setBoolean(FIELD_MM_WARN_CLOSE, flag);
            return this;
        }

        public Boolean isClose() {
            return getBoolean(FIELD_MM_WARN_CLOSE);
        }

        public Boolean hasClose() {
            return isSetField(FIELD_MM_WARN_CLOSE);
        }

    }


}
