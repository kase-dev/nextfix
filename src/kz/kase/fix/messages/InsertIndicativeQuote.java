package kz.kase.fix.messages;

import kz.kase.fix.InsertIndicativeQuoteStatus;
import quickfix.Message;

import static kz.kase.fix.FixProtocol.*;

public class InsertIndicativeQuote extends Message {

    public InsertIndicativeQuote() {
        super(new int[]{
                FIELD_INDICATIVE_QUOTE_REF,
                FIELD_INDICATIVE_QUOTE_INSTR_SYMBOL,
                FIELD_INDICATIVE_QUOTE_BUY_PRICE,
                FIELD_INDICATIVE_QUOTE_SELL_PRICE,
                FIELD_INDICATIVE_QUOTE_BUY_VOLUME,
                FIELD_INDICATIVE_QUOTE_SELL_VOLUME,
                FIELD_INDICATIVE_QUOTE_COMMENT,
                FIELD_INSERT_INDICATIVE_QUOTE_STATUS
        });

        getHeader().setString(FIELD_MESSAGE_TYPE, MESSAGE_INDICATIVE_QUOTE_INSERT);
    }


    public InsertIndicativeQuote setRef(long ref) {
        setLong(FIELD_INDICATIVE_QUOTE_REF, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FIELD_INDICATIVE_QUOTE_REF);
    }

    public boolean hasRef() {
        return isSetField(FIELD_INDICATIVE_QUOTE_REF);
    }

    public InsertIndicativeQuote setInstrSymbol(String symbol) {
        setString(FIELD_INDICATIVE_QUOTE_INSTR_SYMBOL, symbol);
        return this;
    }

    public String getInstrSymbol() {
        return getString(FIELD_INDICATIVE_QUOTE_INSTR_SYMBOL);
    }

    public boolean hasInstrId() {
        return isSetField(FIELD_INDICATIVE_QUOTE_INSTR_SYMBOL);
    }

    public InsertIndicativeQuote setBuyPrice(double price) {
        setDouble(FIELD_INDICATIVE_QUOTE_BUY_PRICE, price);
        return this;
    }

    public Double getBuyPrice() {
        return getDouble(FIELD_INDICATIVE_QUOTE_BUY_PRICE);
    }

    public boolean hasBuyPrice() {
        return isSetField(FIELD_INDICATIVE_QUOTE_BUY_PRICE);
    }

    public InsertIndicativeQuote setSellPrice(double price) {
        setDouble(FIELD_INDICATIVE_QUOTE_SELL_PRICE, price);
        return this;
    }

    public Double getSellPrice() {
        return getDouble(FIELD_INDICATIVE_QUOTE_SELL_PRICE);
    }

    public boolean hasSellPrice() {
        return isSetField(FIELD_INDICATIVE_QUOTE_SELL_PRICE);
    }

    public InsertIndicativeQuote setBuyVol(long vol) {
        setLong(FIELD_INDICATIVE_QUOTE_BUY_VOLUME, vol);
        return this;
    }

    public Long getBuyVol() {
        return getLong(FIELD_INDICATIVE_QUOTE_BUY_VOLUME);
    }

    public boolean hasBuyVol() {
        return isSetField(FIELD_INDICATIVE_QUOTE_BUY_VOLUME);
    }

    public InsertIndicativeQuote setSellVol(long vol) {
        setLong(FIELD_INDICATIVE_QUOTE_SELL_VOLUME, vol);
        return this;
    }

    public Long getSellVol() {
        return getLong(FIELD_INDICATIVE_QUOTE_SELL_VOLUME);
    }

    public boolean hasSellVol() {
        return isSetField(FIELD_INDICATIVE_QUOTE_SELL_VOLUME);
    }

    public InsertIndicativeQuote setComment(String comment) {
        setString(FIELD_INDICATIVE_QUOTE_COMMENT, comment);
        return this;
    }

    public String getComment() {
        return getString(FIELD_INDICATIVE_QUOTE_COMMENT);
    }

    public boolean hasComment() {
        return isSetField(FIELD_INDICATIVE_QUOTE_COMMENT);
    }

    public InsertIndicativeQuote setStatus(InsertIndicativeQuoteStatus status) {
        setInt(FIELD_INSERT_INDICATIVE_QUOTE_STATUS, status.getValue());
        return this;
    }

    public InsertIndicativeQuoteStatus getStatus() {
        return InsertIndicativeQuoteStatus.valueOf(getInt(FIELD_INSERT_INDICATIVE_QUOTE_STATUS));
    }

    public boolean hasStatus() {
        return isSetField(FIELD_INSERT_INDICATIVE_QUOTE_STATUS);
    }

}
