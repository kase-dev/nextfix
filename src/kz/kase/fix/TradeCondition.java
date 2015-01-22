package kz.kase.fix;


public enum TradeCondition implements StringItem {

    Opened("T"),
    Stopped("C"),;

    private final String value;

    private TradeCondition(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static TradeCondition getByValue(String str) {
        for (TradeCondition v : values()) {
            if (v.getValue().equals(str)) return v;
        }
        return null;
    }
}
