package kz.kase.fix;

public enum OrdRejReason implements IntItem {

    BrokerCredit(0),
    UnknownSymbol(1),
    ExchangeClosed(2),
    OrderExceedsLimit(3),
    TooLateToEnter(4),
    UnknownOrder(5),
    DuplicateOrder(6),
    DuplicateOfAVerballyCommunicatedOrder(7),
    StaleOrder(8),
    InvalidInvestorID(9),
    UnsupportedOrderCharacteristic(10),
    IncorrectQuantity(11),
    IncorrectAllocatedQuantity(12),
    UnknownAccount(13),
    UserNotFound(17),
    WrongInstrId(18),
    InstrIsNotOpened(19),
    NotEnoughMoney(20),
    NotEnoughInstr(21),
    WrongAccId(22),
    NoRightOnAcc(23),
    ZeroQty(24),
    MemberNotFound(25),
    NoRightsOnInstrument(26),
    OrderTypeIsNotEnabled(27),
    OrderSideIsNotEnabled(28),
    LessMinQty(29),
    MoreMaxQty(30),
    AliquantQty(31),
    WrongPrice(32),
    WrongExpirationDate(33),
    LastDealPriceDeviationExceeded(34),
    LastDayPriceDeviationExceeded(35),
    MoneyLimitExceeded(36),
    InstrLimitExceeded(37),
    NoOpponentsToMatch(38),
    InstrPosNotFound(39),
    InstrIsBlocked(40),
    AccountIsBLocked(41),
    NoSessionForSwapCalcFound(42),
    StoppedOnSameAccount(43),
    TransactionError(44),
    WrongAccountMarketType(45),
    Other(99);


    private final int value;

    private OrdRejReason(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static OrdRejReason valueOf(Integer c) {
        if (c == null) return null;
        for (OrdRejReason v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
