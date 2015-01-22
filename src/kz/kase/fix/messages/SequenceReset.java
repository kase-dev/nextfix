package kz.kase.fix.messages;

import kz.kase.fix.FixProtocol;
import quickfix.Message;

public class SequenceReset extends Message {


    public SequenceReset() {
        this(false);
    }

    public SequenceReset(boolean parse) {
        super();
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_SEQUENCE_RESET);
    }


    //todo fields


}
