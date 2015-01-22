package kz.kase.fix.extend;

import kz.kase.fix.IntItem;

public enum PosItemType implements IntItem {

    INITIAL(0),
    CURRENT(1),
    BLOCKED(2),
    PLANNED(3);

    private final int value;

    private PosItemType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static PosItemType valueOf(Integer c) {
        if (c == null) return null;
        for (PosItemType v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
