package kz.kase.fix.messages;


import kz.kase.fix.FixProtocol;
import quickfix.Group;
import quickfix.Message;
import quickfix.field.StringField;

import static kz.kase.fix.FixProtocol.*;

public class PositionTransferInstruction extends Message {

    public PositionTransferInstruction() {
        super();
        getHeader().setString(FIELD_MESSAGE_TYPE, MESSAGE_POS_TRANSFER_INSTRUCTION);
    }


    public PositionTransferInstruction setFromAccount(String acc) {
        setString(FIELD_ACCOUNT, acc);
        return this;
    }

    public String getFromAccount() {
        return getString(FIELD_ACCOUNT);
    }

    public PositionTransferInstruction setToAccount(String account) {
        setString(FIELD_TRANSFER_INSTRUCTION_ID, account);
        return this;
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

    public String getToAccount() {
        return getString(FIELD_TRANSFER_INSTRUCTION_ID);
    }

    public String getSymbol() {
        return getString(FIELD_SYMBOL);
    }


    public static class PositionQty extends Group {
        public PositionQty() {
            super(FixProtocol.FIELD_NO_POSITIONS, FixProtocol.FIELD_POSITION_TYPE,
                    new int[]{
                            703, 704
                    }
            );
        }

        public PositionQty setPosType(String posType) {
            setString(FixProtocol.FIELD_POSITION_TYPE, posType);
            return this;
        }

        public String getPosType() {
            return getString(FixProtocol.FIELD_POSITION_TYPE);
        }

        public PositionQty setLongQty(Double pos) {
            setDouble(FixProtocol.FIELD_LONG_QTY, pos);
            return this;
        }

        public Double getLongQty() {
            return getDouble(FixProtocol.FIELD_LONG_QTY);
        }

    }

    public PositionTransferInstruction setCurrency(String currency) {
        setString(FIELD_CURRENCY, currency);
        return this;
    }

    public String getCurrency() {
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
