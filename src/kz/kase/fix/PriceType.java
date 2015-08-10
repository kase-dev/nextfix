package kz.kase.fix;


public enum PriceType implements IntItem {

    Percentage(1),
    PerUnit(2),
    FixedAmount(3),
    Discount(4),
    Percentage_dirty(5),
    Spread(6),
    Yield(9),;

    private final int value;

    private PriceType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static PriceType valueOf(int c) {
        for (PriceType v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
