package kz.kase.fix.messages;

import quickfix.Message;

import static kz.kase.fix.FixProtocol.*;

public class MMLiabilityRequest extends Message {

    public MMLiabilityRequest() {
        super(new int[]{
                FIELD_MM_REQUEST_REF
        });

        getHeader().setString(FIELD_MESSAGE_TYPE, MESSAGE_MM_LIABILITY_REQUEST);
    }


    public MMLiabilityRequest setRef(long ref) {
        setLong(FIELD_MM_REQUEST_REF, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FIELD_MM_REQUEST_REF);
    }

    public boolean hasRef() {
        return isSetField(FIELD_MM_REQUEST_REF);
    }

}
