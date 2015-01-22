package kz.kase.fix;

public enum PosUpdateAction implements IntItem {
    NEW(0),
    CHANGE(1),
    DELETE(2);


    private final int value;

    private PosUpdateAction(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static PosUpdateAction valueOf(Integer c) {
        if (c == null) return null;
        for (PosUpdateAction v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
