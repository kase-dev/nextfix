package kz.kase.fix;

public enum SecurityListRequestType implements IntItem {

    SYMBOL(0),
    SECURITYTYPE_AND_OR_CFICODE(1),
    PRODUCT(2),
    TRADINGSESSIONID(3),
    ALL_SECURITIES(4);

    private final int value;

    private SecurityListRequestType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static SecurityListRequestType valueOf(Integer c) {
        if (c == null) return null;
        for (SecurityListRequestType v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
