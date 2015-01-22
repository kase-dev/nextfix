package kz.kase.fix;


public enum SubscriptionType implements CharItem {

    SNAPSHOT('0'),
    SNAPSHOT_AND_UPDATES('1'),
    DISABLE_PREVIOUS_SNAPSHOT('2');

    private final char value;

    private SubscriptionType(char value) {
        this.value = value;
    }

    @Override
    public char getValue() {
        return value;
    }

    public static SubscriptionType valueOf(char c) {
        for (SubscriptionType v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
