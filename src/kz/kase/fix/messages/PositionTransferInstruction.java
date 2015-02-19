package kz.kase.fix.messages;


import kz.kase.fix.FixProtocol;
import quickfix.Group;
import quickfix.Message;
import quickfix.field.StringField;

import java.util.Date;

import static kz.kase.fix.FixProtocol.*;

public class PositionTransferInstruction extends Message {

    public PositionTransferInstruction() {
        this(false);
    }

    public PositionTransferInstruction(boolean parse) {
        super();
        getHeader().setString(FIELD_MESSAGE_TYPE, MESSAGE_POS_TRANSFER_INSTRUCTION);
        /*if (!parse) {
            setUtcTimeStamp(FIELD_TRANSACTION_TIME, new Date());
        }*/
    }

    public PositionTransferInstruction setRef(long ref) {
        setField(new StringField(FIELD_TRANSFER_INSTRUCTION_ID, Long.toString(ref)));
        return this;
    }

    public Long getRef() {
        return getLong(FIELD_TRANSFER_INSTRUCTION_ID);
    }

    public PositionTransferInstruction setSymbol(String symbol) {
        setString(FIELD_SYMBOL, symbol);
        return this;
    }

    public String getSymbol(){
        return getString(FIELD_SYMBOL);
    }

    public PositionTransferInstruction setCurrency(String currency) {
        setString(FIELD_CURRENCY, currency);
        return this;
    }

    public String getCurrency(){
        return getString(FIELD_CURRENCY);
    }

    public static class NoTargetPartyIDs extends Group {

        public NoTargetPartyIDs() {
            super(FIELD_NO_TARGET_PARTIES, FIELD_TARGET_PARTY_ID,
                    new int[]{
                            1462
                    });
        }

        public NoTargetPartyIDs setTargetPartyID(String type) {
            setString(FixProtocol.FIELD_TARGET_PARTY_ID, type);
            return this;
        }

        public String getTargetPartyID() {
            return getString(FixProtocol.FIELD_TARGET_PARTY_ID);
        }

    }

}
