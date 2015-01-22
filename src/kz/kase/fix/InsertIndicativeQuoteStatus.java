package kz.kase.fix;

public enum InsertIndicativeQuoteStatus implements IntItem {

    Ok(0),
    WrongInstrumentId(1),
    InstrumentNotFound(2),
    InstrumentIsNotOpened(3),
    UserNotFound(4),
    WrongPrice(5),
    WrongVolume(6),
    AlreadySet(7),
    IndicativeNotAllowed(8),
    AliquantQty(9),
    LessMinQty(10),
    MoreMaxQty(11),
    TransactionError(99);


    private final int value;

    private InsertIndicativeQuoteStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static InsertIndicativeQuoteStatus valueOf(Integer c) {
        if (c == null) return null;
        for (InsertIndicativeQuoteStatus v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
