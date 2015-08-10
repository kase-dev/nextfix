package kz.kase.fix;

public enum PositionType implements StringItem {

    AllocationTradeQty("ALC"),
    OptionAssignment("AS"),
    AsOfTradeQty("ASF"),
    CurrentPos("CUR"),
    PlannedBuy("PB"),
    PlannedSell("PS"),
    Bought("B"),
    Sold("S"),
    AvgBought("AB"),
    AvgSold("AS"),
    Margin("M"),
    Blocked("B");


    private final String value;

    private PositionType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

}
