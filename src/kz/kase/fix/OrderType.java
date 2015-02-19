package kz.kase.fix;

public enum OrderType implements CharItem {
    MARKET('1'),
    LIMIT('2'),
    STOP('3'),
    STOP_LIMIT('4'),
    //   MARKET_ON_CLOSE('5'),
//   WITH_OR_WITHOUT('6'),
//   LIMIT_OR_BETTER('7'),
//   LIMIT_WITH_OR_WITHOUT('8'),
//   ON_BASIS('9'),
//   ON_CLOSE('A'),
//   LIMIT_ON_CLOSE('B'),
//   FOREX_MARKET('C'),
//   PREVIOUSLY_QUOTED('D'),
//   PREVIOUSLY_INDICATED('E'),
//   FOREX_LIMIT('F'),
    FOREX_SWAP('G'),
    REPO('R')
//   FOREX_PREVIOUSLY_QUOTED('H'),
//   FUNARI('I'),
//   MARKET_IF_TOUCHED('J'),
//   MARKET_WITH_LEFTOVER_AS_LIMIT('K'),
//   PREVIOUS_FUND_VALUATION_POINT('L'),
//   NEXT_FUND_VALUATION_POINT('M'),
//   PEGGED('P'),
//   COUNTER_ORDER_SELECTION('Q')
    ;


    private final char value;

    private OrderType(char value) {
        this.value = value;
    }

    @Override
    public char getValue() {
        return value;
    }

    public static OrderType valueOf(Character c) {
        if (c == null) return null;
        for (OrderType v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
