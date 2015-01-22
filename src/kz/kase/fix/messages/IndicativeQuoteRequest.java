package kz.kase.fix.messages;

import quickfix.Message;

import static kz.kase.fix.FixProtocol.*;

public class IndicativeQuoteRequest extends Message{

    public IndicativeQuoteRequest() {
        super(new int[]{
                FIELD_INDICATIVE_QUOTE_REF
        });

        getHeader().setString(FIELD_MESSAGE_TYPE, MESSAGE_INDICATIVE_QUOTE_REQUEST);
    }


    public IndicativeQuoteRequest setRef(long ref) {
        setLong(FIELD_INDICATIVE_QUOTE_REF, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FIELD_INDICATIVE_QUOTE_REF);
    }

    public boolean hasRef() {
        return isSetField(FIELD_INDICATIVE_QUOTE_REF);
    }
}
