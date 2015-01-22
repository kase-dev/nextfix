package kz.kase.fix;


public enum PosTransType implements IntItem {

    EXERCISE(1),
    DO_NOT_EXERCISE(2),
    POSITION_ADJUSTMENT(3),
    POSITION_CHANGE_SUBMISSION(4),
    PLEDGE(5);

    private final int value;

    private PosTransType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static PosTransType valueOf(int c) {
        for (PosTransType v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
