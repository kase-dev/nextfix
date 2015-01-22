package kz.kase.fix.components;

import static kz.kase.fix.FixProtocol.*;

public class BaseTradingRules extends quickfix.MessageComponent {


    private static final int[] componentFields = {
            562, 1140, 1143, 1245, 561, 423
    };

    private static final int[] groupField = new int[0];

    @Override
    protected int[] getFields() {
        return componentFields;
    }

    @Override
    protected int[] getGroupFields() {
        return groupField;
    }

    public BaseTradingRules setMinTradeVol(int vol) {
        setInt(FIELD_MIN_TRADE_VOL, vol);
        return this;
    }

    public int getMinTradeVol() {
        return getInt(FIELD_MIN_TRADE_VOL);
    }

    public BaseTradingRules setMaxTradeVol(int vol) {
        setInt(FIELD_MAX_TRADE_VOL, vol);
        return this;
    }

    public int getMaxTradeVol() {
        return getInt(FIELD_MAX_TRADE_VOL);
    }

    public BaseTradingRules setMaxPriceVariation(double price) {
        setDouble(FIELD_MAX_PRICE_VARIATION, price);
        return this;
    }

    public double getMaxPriceVariation() {
        return getDouble(FIELD_MAX_PRICE_VARIATION);
    }

    public BaseTradingRules setTradingCurrency(String cur) {
        setString(FIELD_TRADING_CURRENCY, cur);
        return this;
    }

    public String gatTradingCurrency() {
        return getString(FIELD_TRADING_CURRENCY);
    }

    public BaseTradingRules setRoundLot(int lot) {
        setInt(FIELD_ROUND_LOT, lot);
        return this;
    }

    public int getRoundLot() {
        return getInt(FIELD_ROUND_LOT);
    }


    public BaseTradingRules setPriceType(int type) {
        setInt(FIELD_PRICE_TYPE, type);
        return this;
    }

    public int getPriceType() {
        return getInt(FIELD_PRICE_TYPE);
    }

}
