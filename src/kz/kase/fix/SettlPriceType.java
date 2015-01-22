package kz.kase.fix;


public enum SettlPriceType implements IntItem {

    Final(1),
    Theoretical(2);

    private final int value;

    private SettlPriceType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static SettlPriceType valueOf(int c) {
        for (SettlPriceType v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
