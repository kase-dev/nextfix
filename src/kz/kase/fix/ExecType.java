package kz.kase.fix;

public enum ExecType implements CharItem {

    NEW('0'),
    PARTIAL_FILL('1'),
    FILL('2'),
    DONE_FOR_DAY('3'),
    CANCELED('4'),
    REPLACE('5'),
    PENDING_CANCEL('6'),
    STOPPED('7'),
    REJECTED('8'),
    SUSPENDED('9'),
    PENDING_NEW('A'),
    CALCULATED('B'),
    EXPIRED('C'),
    RESTATED('D'),
    PENDING_REPLACE('E'),
    TRADE('F'),
    TRADE_CORRECT('G'),
    TRADE_CANCEL('H'),
    ORDER_STATUS('I'),
    TRADE_IN_A_CLEARING_HOLD('J'),
    TRADE_HAS_BEEN_RELEASED_TO_CLEARING('K'),
    TRIGGERED_OR_ACTIVATED_BY_SYSTEM('L');

    private final char value;

    private ExecType(char value) {
        this.value = value;
    }

    @Override
    public char getValue() {
        return value;
    }

    public static ExecType valueOf(char c) {
        for (ExecType v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
