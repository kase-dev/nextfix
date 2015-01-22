package kz.kase.fix.messages;

import kz.kase.fix.IndicativeQuoteCancelStatus;
import quickfix.Message;

import static kz.kase.fix.FixProtocol.*;

public class IndicativeQuoteCancel extends Message{

    public IndicativeQuoteCancel() {
        super(new int[]{
                FIELD_INDICATIVE_QUOTE_REF,
                FIELD_INDICATIVE_QUOTE_ID,
                FIELD_CANCEL_INDICATIVE_QUOTE_STATUS
        });

        getHeader().setString(FIELD_MESSAGE_TYPE, MESSAGE_INDICATIVE_QUOTE_CANCEL);
    }


    public IndicativeQuoteCancel setRef(long ref) {
        setLong(FIELD_INDICATIVE_QUOTE_REF, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FIELD_INDICATIVE_QUOTE_REF);
    }

    public boolean hasRef() {
        return isSetField(FIELD_INDICATIVE_QUOTE_REF);
    }

    public IndicativeQuoteCancel setId(long id) {
        setLong(FIELD_INDICATIVE_QUOTE_ID, id);
        return this;
    }

    public Long getId() {
        return getLong(FIELD_INDICATIVE_QUOTE_ID);
    }

    public boolean hasId() {
        return isSetField(FIELD_INDICATIVE_QUOTE_ID);
    }

    public IndicativeQuoteCancel setStatus(IndicativeQuoteCancelStatus status) {
        setInt(FIELD_CANCEL_INDICATIVE_QUOTE_STATUS, status.getValue());
        return this;
    }

    public IndicativeQuoteCancelStatus getStatus() {
        return IndicativeQuoteCancelStatus.valueOf(getInt(FIELD_CANCEL_INDICATIVE_QUOTE_STATUS));
    }

    public boolean hasStatus() {
        return isSetField(FIELD_CANCEL_INDICATIVE_QUOTE_STATUS);
    }
}
