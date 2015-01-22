package kz.kase.fix;


public enum MDEntryType implements CharItem {

    BID('0'),
    OFFER('1'),
    TRADE('2'),
    INDEX_VALUE('3'),
    OPENING_PRICE('4'),
    CLOSING_PRICE('5'),
    SETTLEMENT_PRICE('6'),
    TRADING_SESSION_HIGH_PRICE('7'),
    TRADING_SESSION_LOW_PRICE('8'),
    TRADING_SESSION_VWAP_PRICE('9'),
    IMBALANCE('A'),
    TRADE_VOLUME('B'),
    OPEN_INTEREST('C'),
    COMPOSITE_UNDERLYING_PRICE('D'),
    SIMULATED_SELL_PRICE('E'),
    SIMULATED_BUY_PRICE('F'),
    MARGIN_RATE('G'),
    MID_PRICE('H'),
    EMPTY_BOOK('J'),
    SETTLE_HIGH_PRICE('K'),
    SETTLE_LOW_PRICE('L'),
    PRIOR_SETTLE_PRICE('M'),
    SESSION_HIGH_BID('N'),
    SESSION_LOW_OFFER('O'),
    EARLY_PRICES('P'),
    AUCTION_CLEARING_PRICE('Q'),

//    AUCTION_CLEARING_PRICE('Q')
    ;


    private final char value;

    private MDEntryType(char value) {
        this.value = value;
    }

    @Override
    public char getValue() {
        return value;
    }

    public static MDEntryType valueOf(char c) {
        for (MDEntryType v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
