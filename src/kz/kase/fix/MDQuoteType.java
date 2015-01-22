package kz.kase.fix;

public enum MDQuoteType implements IntItem {

    Indicative(0),
    Tradeable(1),
    RestrictedTradeable(2),
    Counter(3),
    IndicativeAndTradeable(4);


    private final int value;

    private MDQuoteType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static MDQuoteType valueOf(Integer c) {
        if (c == null) return null;
        for (MDQuoteType v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
