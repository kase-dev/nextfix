package kz.kase.fix;

public enum BusinessRejectReason implements IntItem {

    OTHER(0),
    UNKOWN_ID(1),
    UNKNOWN_SECURITY(2),
    UNSUPPORTED_MESSAGE_TYPE(3),
    APPLICATION_NOT_AVAILABLE(4),
    CONDITIONALLY_REQUIRED_FIELD_MISSING(5),
    NOT_AUTHORIZED(6),
    DELIVERTO_FIRM_NOT_AVAILABLE_AT_THIS_TIME(7),
    INVALID_PRICE_INCREMENT(18);


    private final int value;

    private BusinessRejectReason(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static BusinessRejectReason valueOf(Integer c) {
        if (c == null) return null;
        for (BusinessRejectReason v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
