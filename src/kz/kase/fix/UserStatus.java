package kz.kase.fix;

public enum UserStatus implements IntItem {

    PASS_WEAK(0),
    USER_NOT_FOUND(3),
    PASS_INCORRECT(4),
    PASS_CHANGED(5),
    OTHER(6);

    private final int value;

    private UserStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static UserStatus valueOf(Integer c) {
        if (c == null) return null;
        for (UserStatus v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
