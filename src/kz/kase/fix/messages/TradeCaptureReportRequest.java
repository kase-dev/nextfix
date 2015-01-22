package kz.kase.fix.messages;

import kz.kase.fix.FixProtocol;
import quickfix.Message;

import java.util.Date;

import static kz.kase.fix.FixProtocol.*;

public class TradeCaptureReportRequest extends Message {


    public TradeCaptureReportRequest() {
        super();
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_TRADE_CAPT_REP_REQ);
    }

    public TradeCaptureReportRequest setTradeReqId(String id) {
        setString(FIELD_TRADER_REQ_ID, id);
        return this;
    }

    public String getReportReqId() {
        return getString(FIELD_TRADER_REQ_ID);
    }

    public boolean hasReqId() {
        return isSetField(FIELD_TRADER_REQ_ID);
    }

    public TradeCaptureReportRequest setTradeReqType(int id) {
        setInt(FIELD_TRADER_RQ_TYPE, id);
        return this;
    }

    public Integer getReportReqType() {
        return getInt(FIELD_TRADER_RQ_TYPE);
    }

    public boolean hasReqType() {
        return isSetField(FIELD_TRADER_RQ_TYPE);
    }

    public TradeCaptureReportRequest setSymbol(String symbol) {
        setString(FIELD_SYMBOL, symbol);
        return this;
    }

    public String getSymbol() {
        return getString(FIELD_SYMBOL);
    }

    public boolean hasSymbol() {
        return isSetField(FIELD_SYMBOL);
    }

    public TradeCaptureReportRequest setDatedDate(Date date) {
        setUtcDateOnly(FIELD_DATED_DATE, date);
        return this;
    }

    public Date getDatedDate() {
        return getUtcDateOnly(FIELD_DATED_DATE);
    }

    public boolean hasDatedDate() {
        return isSetField(FIELD_DATED_DATE);
    }

}
