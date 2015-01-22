package kz.kase.fix;

public enum OrderStatus implements CharItem {
    NEW('0'),
    PARTIALLY_FILLED('1'),
    FILLED('2'),
    DONE_FOR_DAY('3'),
    CANCELED('4'),
    REPLACED('5'),
    PENDING_CANCEL('6'),
    STOPPED('7'),
    REJECTED('8'),
    SUSPENDED('9'),
    PENDING_NEW('A'),
    CALCULATED('B'),
    EXPIRED('C'),
    ACCEPTED_FOR_BIDDING('D'),
    PENDING_REPLACE('E');

    private final char value;

    private OrderStatus(char value) {
        this.value = value;
    }

    @Override
    public char getValue() {
        return value;
    }

    public static OrderStatus valueOf(char c) {
        for (OrderStatus v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
