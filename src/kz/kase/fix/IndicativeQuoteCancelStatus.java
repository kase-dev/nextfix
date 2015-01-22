package kz.kase.fix;

public enum IndicativeQuoteCancelStatus implements IntItem {

    Ok(0),
    QuoteNotFound(1),
    UserNotFound(2),
    NoRightsOnObject(3),
    InstrumentIsNotOpened(4),
    TransactionError(99);

    private final int value;

    private IndicativeQuoteCancelStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static IndicativeQuoteCancelStatus valueOf(Integer c) {
        if (c == null) return null;
        for (IndicativeQuoteCancelStatus v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
