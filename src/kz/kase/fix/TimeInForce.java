package kz.kase.fix;

public enum TimeInForce implements CharItem {

    DAY('0'),
    GOOD_TILL_CANCEL('1'),
    AT_THE_OPENING('2'),
    IMMEDIATE_OR_CANCEL('3'),
    FILL_OR_KILL('4'),
    GOOD_TILL_CROSSING('5'),
    GOOD_TILL_DATE('6'),
    AT_THE_CLOSE('7');

    private final char value;

    private TimeInForce(char value) {
        this.value = value;
    }

    @Override
    public char getValue() {
        return value;
    }

    public static TimeInForce valueOf(Character c) {
        if (c == null) return null;
        for (TimeInForce v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
