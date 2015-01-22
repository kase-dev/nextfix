package kz.kase.fix;

public enum PosReqResult implements IntItem {
    VALID_REQUEST(0),
    INVALID_OR_UNSUPPORTED_REQUEST(1),
    NO_POSITIONS_FOUND_THAT_MATCH_CRITERIA(2),
    NOT_AUTHORIZED_TO_REQUEST_POSITIONS(3),
    REQUEST_FOR_POSITION_NOT_SUPPORTED(4),
    OTHER(99),;


    private final int value;

    private PosReqResult(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static PosReqResult valueOf(Integer c) {
        if (c == null) return null;
        for (PosReqResult v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
