package kz.kase.fix.messages;

import quickfix.Message;

import static kz.kase.fix.FixProtocol.*;

public class PositionTransferReport extends Message {

    public PositionTransferReport() {
        super();
        getHeader().setString(FIELD_MESSAGE_TYPE, MESSAGE_POS_TRANSFER_REPORT);
    }

    public enum TransferStatus {
        ACCEPTED(3),
        DECLINED(4),
        CANCELLED(5);

        private int value;

        TransferStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static TransferStatus valueOf(int stat) {
            for (TransferStatus v : values()) {
                if (v.getValue() == stat) {
                    return v;
                }
            }
            return null;
        }

    }

    public long getRef() {
        return getLong(FIELD_TRANSFER_INSTRUCTION_ID);
    }

    public PositionTransferReport setRef(long ref) {
        setLong(FIELD_TRANSFER_INSTRUCTION_ID, ref);
        return this;
    }

    public PositionTransferReport setTransferStatus(TransferStatus ts) {
        setInt(FIELD_TRANSFER_STATUS, ts.getValue());
        return this;
    }

    public TransferStatus getTransferStatus() {
        return TransferStatus.valueOf(getInt(FIELD_TRANSFER_STATUS));
    }

    public boolean hasTransferStatus() {
        return isSetField(FIELD_TRANSFER_STATUS);
    }

}
