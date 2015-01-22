package kz.kase.fix;


public enum PosMaintAction implements IntItem {

    NEW(1),
    REPLACE(2),
    CANCEL(3);

    private final int value;

    private PosMaintAction(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static PosMaintAction valueOf(int c) {
        for (PosMaintAction v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
