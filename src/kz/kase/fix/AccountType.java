package kz.kase.fix;

public enum AccountType implements IntItem {

    ACCOUNT_IS_CARRIED_ON_CUSTOMER_SIDE_OF_BOOKS(1),
    ACCOUNT_IS_CARRIED_ON_NON_CUSTOMER_SIDE_OF_BOOKS(2),
    HOUSE_TRADER(3),
    FLOOR_TRADER(4),
    ACCOUNT_IS_CARRIED_ON_NON_CUSTOMER_SIDE_OF_BOOKS_AND_IS_CROSS_MARGINED(6),
    ACCOUNT_IS_HOUSE_TRADER_AND_IS_CROSS_MARGINED(7),
    JOINT_BACKOFFICE_ACCOUNT(8);


    private final int value;

    private AccountType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static AccountType valueOf(Integer c) {
        if (c == null) return null;
        for (AccountType v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
