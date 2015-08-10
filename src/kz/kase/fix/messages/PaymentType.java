package kz.kase.fix.messages;


public enum PaymentType {

    ACTUAL_365(0),
    A30_360(1),
    ACTUAL_360(2),
    ACTUAL_364(3),
    ACTUAL_182_183(4),
    ACTUAL_ACTUAL(5);

    /*ONE_ONE(0),
    THIRTY_THREE_SIXTY_US(1),
    THIRTY_THREE_SIXTY_SIA(2),
    THIRTY_THREE_SIXTY_M(3);

  */
    private int value;

    PaymentType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PaymentType valueOf(int val) {
        for (PaymentType v : values()) {
            if (v.value == val) return v;
        }
        return null;
    }
}
