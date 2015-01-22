package kz.kase.fix;


public enum Side implements CharItem {
    BUY('1'),
    SELL('2'),
    BUY_MINUS('3'),
    SELL_PLUS('4'),
    SELL_SHORT('5'),
    SELL_SHORT_EXEMPT('6'),
    UNDISCLOSED('7'),
    CROSS('8'),
    CROSS_SHORT('9'),
    CROSS_SHORT_EXEMPT('A'),
    AS_DEFINED('B'),
    OPPOSITE('C'),
    SUBSCRIBE('D'),
    REDEEM('E'),
    LEND('F'),
    BORROW('G');

    private final char value;

    private Side(char value) {
        this.value = value;
    }

    @Override
    public char getValue() {
        return value;
    }

    public static Side valueOf(Character c) {
        if (c == null) return null;
        for (Side v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }


    public static void main(String[] args) {
        int value = BUY.getValue();
        System.out.println(value);
    }

}
