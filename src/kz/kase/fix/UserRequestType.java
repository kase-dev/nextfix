package kz.kase.fix;

public enum UserRequestType implements IntItem {

    LOGON_USER(1),
    LOGOUT_USER(2),
    CHANGE_PASS(3),
    REQ_IND_US(4);

    private final int value;

    private UserRequestType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static UserRequestType valueOf(Integer c) {
        if (c == null) return null;
        for (UserRequestType v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
