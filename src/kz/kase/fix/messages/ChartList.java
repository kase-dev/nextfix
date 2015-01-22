package kz.kase.fix.messages;

import kz.kase.fix.FixProtocol;
import quickfix.Group;
import quickfix.Message;

import java.util.Date;
import java.util.List;

import static kz.kase.fix.FixProtocol.*;

public class ChartList extends Message {


    public ChartList() {
        super(new int[]{
                FIELD_CHART_REQUEST_REF,
                FIELD_SYMBOL,
                FIELD_CHART_FROM_DATE,
                FIELD_CHART_TO_DATE,
                FIELD_CHART_PERIOD
        });
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_CHART_LIST);
    }

    public ChartList setRef(long ref) {
        setLong(FixProtocol.FIELD_CHART_REQUEST_REF, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FixProtocol.FIELD_CHART_REQUEST_REF);
    }

    public boolean hasRef() {
        return isSetField(FixProtocol.FIELD_CHART_REQUEST_REF);
    }


    public ChartList setSymbol(String symbol) {
        setString(FixProtocol.FIELD_SYMBOL, symbol);
        return this;
    }

    public String getSymbol() {
        return getString(FixProtocol.FIELD_SYMBOL);
    }

    public boolean hasSymbol() {
        return isSetField(FixProtocol.FIELD_SYMBOL);
    }


    public ChartList setFromDate(Date date) {
        setUtcDateOnly(FixProtocol.FIELD_CHART_FROM_DATE, date);
        return this;
    }

    public Date getFromDate() {
        return getUtcDateOnly(FixProtocol.FIELD_CHART_FROM_DATE);
    }

    public boolean hasFromDate() {
        return isSetField(FixProtocol.FIELD_CHART_FROM_DATE);
    }

    public ChartList setToDate(Date date) {
        setUtcDateOnly(FixProtocol.FIELD_CHART_TO_DATE, date);
        return this;
    }


    public Date getToDate() {
        return getUtcDateOnly(FixProtocol.FIELD_CHART_TO_DATE);
    }

    public boolean hasToDate() {
        return isSetField(FixProtocol.FIELD_CHART_TO_DATE);
    }


    public int getChartsCount() {
        List<Group> grps = getGroups(FIELD_CHART_NO_ENTRIES);
        return grps != null ? grps.size() : 0;
    }


    public static class NoChartEntries extends Group {

        public NoChartEntries() {
            super(FIELD_CHART_NO_ENTRIES, FIELD_CHART_ENTRY_DATE,
                    new int[]{
                            FIELD_CHART_ENTRY_DATE,
                            FIELD_CHART_ENTRY_OPEN_PRICE,
                            FIELD_CHART_ENTRY_HIGH_PRICE,
                            FIELD_CHART_ENTRY_LOW_PRICE,
                            FIELD_CHART_ENTRY_CLOSE_PRICE,
                            FIELD_CHART_ENTRY_VOLUME,
                    }
            );
        }


        public NoChartEntries setDate(Date date) {
            setUtcDateOnly(FIELD_CHART_ENTRY_DATE, date);
            return this;
        }

        public NoChartEntries setOpenPrice(double price) {
            setDouble(FIELD_CHART_ENTRY_OPEN_PRICE, price);
            return this;
        }

        public NoChartEntries setHighPrice(double price) {
            setDouble(FIELD_CHART_ENTRY_HIGH_PRICE, price);
            return this;
        }

        public NoChartEntries setLowPrice(double price) {
            setDouble(FIELD_CHART_ENTRY_LOW_PRICE, price);
            return this;
        }

        public NoChartEntries setClosePrice(double price) {
            setDouble(FIELD_CHART_ENTRY_CLOSE_PRICE, price);
            return this;
        }

        public NoChartEntries setVolume(double volume) {
            setDouble(FIELD_CHART_ENTRY_VOLUME, volume);
            return this;
        }


    }

}
