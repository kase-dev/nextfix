package kz.kase.fix.messages;


public enum RedemptionPriceType {

    PERCENTAGE(1),
    PER_UNIT(2),
    FIXED(3),
    DISCOUNT_PERCENTAGE(4),
    PREMIUM_PERCENTAGE(5),
    SPREAD(6),
    TED_PRICE(7),
    TED_YIELD(8),
    YIELD(9);


    private final int value;

    RedemptionPriceType(int value) {
        this.value = value;
    }

    public static RedemptionPriceType valueOf(int value) {
        for (RedemptionPriceType r : values()) {
            if (r.value == value) return r;
        }
        return null;
    }

    public int getValue() {
        return value;
    }


/*            3	=
    Fixed amount (absolute value)
            4	=
    Discount - percentage points below par
            5	=
    Premium - percentage points over par
            6	=
    Spread (basis points spread)
            7	=
    TED Price
            8	=
    TED Yield
            9	=
    Yield
            10	=
    Fixed cabinet trade price (primarily for listed futures and options)
            11	=
    Variable cabinet trade price (primarily for listed futures and options)
            13	=
    Product ticks in halfs
            14	=
    Product ticks in fourths
            15	=
    Product ticks in eights
            16	=
    Product ticks in sixteenths
            17	=
    Product ticks in thirty-seconds
            18	=
    Product ticks in sixty-forths
            19	=
    Product ticks in one-twenty-eights*/
}
