package kz.kase.fix.messages;

import kz.kase.fix.FixProtocol;
import quickfix.Message;

import java.util.Date;

import static kz.kase.fix.FixProtocol.*;

public class ChartListRequest extends Message {


    public ChartListRequest() {
        super(new int[]{
                FIELD_CHART_REQUEST_REF,
                FIELD_SYMBOL,
                FIELD_CHART_FROM_DATE,
                FIELD_CHART_TO_DATE,
                FIELD_CHART_PERIOD
        });
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_CHART_LIST_REQUEST);
    }

    public ChartListRequest setRef(long ref) {
        setLong(FixProtocol.FIELD_CHART_REQUEST_REF, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FixProtocol.FIELD_CHART_REQUEST_REF);
    }

    public boolean hasRef() {
        return isSetField(FixProtocol.FIELD_CHART_REQUEST_REF);
    }


/*
    public ChartListRequest setInstrId(long instrId) {
        setLong(FixProtocol.FIELD_CHART_INSTR_ID, instrId);
        return this;
    }

    public Long getInstrId() {
        return getLong(FixProtocol.FIELD_CHART_INSTR_ID);
    }

    public boolean hasInstrId() {
        return isSetField(FixProtocol.FIELD_CHART_INSTR_ID);
    }
*/

    public ChartListRequest setSymbol(String symbol) {
        setString(FixProtocol.FIELD_SYMBOL, symbol);
        return this;
    }

    public String getSymbol() {
        return getString(FixProtocol.FIELD_SYMBOL);
    }

    public boolean hasSymbol() {
        return isSetField(FixProtocol.FIELD_SYMBOL);
    }


    public ChartListRequest setFromDate(Date date) {
        setUtcDateOnly(FixProtocol.FIELD_CHART_FROM_DATE, date);
        return this;
    }

    public Date getFromDate() {
        return getUtcDateOnly(FixProtocol.FIELD_CHART_FROM_DATE);
    }

    public boolean hasFromDate() {
        return isSetField(FixProtocol.FIELD_CHART_FROM_DATE);
    }

    public ChartListRequest setToDate(Date date) {
        setUtcDateOnly(FixProtocol.FIELD_CHART_TO_DATE, date);
        return this;
    }

    public Date getToDate() {
        return getUtcDateOnly(FixProtocol.FIELD_CHART_TO_DATE);
    }

    public boolean hasToDate() {
        return isSetField(FixProtocol.FIELD_CHART_TO_DATE);
    }


}
