package kz.kase.fix;

public enum PositionType implements StringItem {

    AllocationTradeQty("ALC"),
    OptionAssignment("AS"),
    AsOfTradeQty("ASF"),
    //todo add rest types...
    ;

    private final String value;

    private PositionType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }


}
