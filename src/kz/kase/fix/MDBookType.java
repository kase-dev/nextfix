package kz.kase.fix;


public enum MDBookType implements IntItem {

    TopOfBook(1),
    PriceDepth(2),
    OrderDepth(3),
    ;

    private final int value;

    private MDBookType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static MDBookType valueOf(int c) {
        for (MDBookType v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
