package kz.kase.fix;

public enum SecurityTradingStatus implements IntItem {

    Opening_delay(1),
    Trading_halt(2),
    Resume(3),
    No_Open_No_Resume(4),
    Price_indication(5),
    Trading_Range_Indication(6),
    Market_Imbalance_Buy(7),
    Market_Imbalance_Sell(8),
    Market_Close_Imbalance_Buy(9),
    Market_Close_Imbalance_Sell(10),
    No_Market_Imbalance(12),
    No_Market_Close_Imbalance(13),
    ITS_Pre_opening(14),
    New_Price_Indication(15),
    Trade_Dissemination_Time(16),
    Ready_to_trade(17),
    Not_available_for_trading(18),
    Not_traded_this_market(19),
    Unknown_or_Invalid(20),
    Pre_open(21),
    Opening_Rotation(22),
    Fast_Market(23),
    Pre_Cross(24),
    Cross(25),
    Post_close(26);
    private final int value;

    private SecurityTradingStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static SecurityTradingStatus getByValue(int i) {
        for (SecurityTradingStatus v : values()) {
            if (v.getValue() == i) return v;
        }
        return null;
    }
}
