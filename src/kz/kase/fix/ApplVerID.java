package kz.kase.fix;


public enum ApplVerID implements StringItem {


    FIX27("0"),
    FIX30("1"),
    FIX40("2"),
    FIX41("3"),
    FIX42("4"),
    FIX43("5"),
    FIX44("6"),
    FIX50("7"),
    FIX50SP1("8"),
    FIX50SP2("9");

    private final String value;

    private ApplVerID(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static ApplVerID getByValue(String str) {
        for (ApplVerID v : values()) {
            if (v.getValue().equals(str)) return v;
        }
        return null;
    }
}
