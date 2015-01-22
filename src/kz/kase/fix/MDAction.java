package kz.kase.fix;


public enum MDAction implements CharItem {

    NEW('0'),
    CHANGE('1'),
    DELETE('2'),
    DELETE_THRU('3'),
    DELETE_FROM('4');

    private final char value;

    private MDAction(char value) {
        this.value = value;
    }

    @Override
    public char getValue() {
        return value;
    }

    public static MDAction valueOf(char c) {
        for (MDAction v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
