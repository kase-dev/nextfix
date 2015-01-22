package kz.kase.fix;

public enum Product implements IntItem {

    AGENCY(1),
    COMMODITY(2),
    CORPORATE(3),
    CURRENCY(4),
    EQUITY(5),
    GOVERNMENT(6),
    INDEX(7),
    LOAN(8),
    MONEYMARKET(9),
    MORTGAGE(10),
    MUNICIPAL(11),
    OTHER(12),
    FINANCING(13),
    DERIVATIVE(14);

    private final int value;

    private Product(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static Product valueOf(Integer c) {
        if (c == null) return null;
        for (Product v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
