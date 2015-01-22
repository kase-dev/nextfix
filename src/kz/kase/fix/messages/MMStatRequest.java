package kz.kase.fix.messages;

import quickfix.Message;

import static kz.kase.fix.FixProtocol.*;

public class MMStatRequest extends Message {

    public MMStatRequest() {
        super(new int[]{
                FIELD_MM_REQUEST_REF,
                FIELD_MM_INSTR_ID
        });

        getHeader().setString(FIELD_MESSAGE_TYPE, MESSAGE_MM_STAT_REQUEST);
    }


    public MMStatRequest setRef(long ref) {
        setLong(FIELD_MM_REQUEST_REF, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FIELD_MM_REQUEST_REF);
    }

    public boolean hasRef() {
        return isSetField(FIELD_MM_REQUEST_REF);
    }

    public MMStatRequest setInstrId(long id) {
        setLong(FIELD_MM_INSTR_ID, id);
        return this;
    }

    public Long getInstrId() {
        return getLong(FIELD_MM_INSTR_ID);
    }

    public boolean hasInstrId() {
        return isSetField(FIELD_MM_INSTR_ID);
    }


}
