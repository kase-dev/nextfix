package kz.kase.fix.extend;

import kz.kase.fix.IntItem;

public enum PosKeyType implements IntItem {

    MONEY(1),
    INSTRUMENT(2);


    private final int value;

    private PosKeyType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static PosKeyType valueOf(Integer c) {
        if (c == null) return null;
        for (PosKeyType v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
