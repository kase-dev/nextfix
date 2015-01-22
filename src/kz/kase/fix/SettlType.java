package kz.kase.fix;


public enum SettlType implements StringItem {

    Regular("0"),
    Cash("1"),
    NextDay("2"),
    TPlus2("3"),
    TPlus3("4"),
    TPlus4("5"),
    Future("6"),
    WhenAndIfIssued("7"),
    SellersOption("8"),
    TPlus5("9"),
    BrokenDate("B"),
    FXSpotNextSettlement("C");

    private final String value;

    private SettlType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static SettlType getByValue(String str) {
        for (SettlType v : values()) {
            if (v.getValue().equals(str)) return v;
        }
        return null;
    }
}
