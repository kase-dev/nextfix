package kz.kase.fix.components;

import kz.kase.fix.FixProtocol;
import kz.kase.fix.Product;
import kz.kase.fix.SeсurityStatus;

import java.util.List;

import static kz.kase.fix.FixProtocol.*;

public class Instrument extends quickfix.MessageComponent {

    private static final int[] componentFields = {
            55, 65, 48, 22, 460, /**/ 1151,/**/ 461, 167, 762, 200, 541, 965, 224, 225, 239, 226,
            227, 228, 255, 543, 470, 471, 472, 240, 202, 947, 206, 231, 223, 207,
            106, 348, 349, 107, 350, 351, 691, 667, 875, 876, 873, 874,
            5031, 5032, 5033, 5034, 5035, 5036, 5037, 5038, 5039, 5040,
            5041, 5042, 5043, 5044, 5045, 5046, 5047, 5108, 5109, 5110, 5111, 5112, 5113, 5114,
            5190, 5191, 5192, 5193, 5194, 320, 969
    };
    private static final int[] componentGroups = {454, 864,};

    public Instrument() {
    }

    @Override
    protected int[] getFields() {
        return componentFields;
    }

    @Override
    protected int[] getGroupFields() {
        return componentGroups;
    }


    public Instrument setSymbol(String symbol) {
        setString(FixProtocol.FIELD_SYMBOL, symbol);
        return this;
    }

    public Instrument setSecReqId(String id) {
        setString(FixProtocol.FIELD_SEC_REQ_ID, id);
        return this;
    }

    public String getSecReqId() {
        return getString(FixProtocol.FIELD_SEC_REQ_ID);
    }

    public String getSymbol() {
        return getString(FixProtocol.FIELD_SYMBOL);

    }

/*
    public Instrument setLot(long lot) {
        setLong(FixProtocol.FIELD_LOT_VALUE, lot);
        return this;
    }
*/

    public long getLot() {
        return getInt(FixProtocol.FIELD_LOT_VALUE);
    }

    public Instrument setNominal(double nominal) {
        setDouble(FixProtocol.FIELD_NOMINAL_VALUE, nominal);
        return this;
    }

    public Instrument setNin(String nin) {
        setString(FixProtocol.FIELD_SECURITY_ID, nin);
        return this;
    }
/*

    public Instrument setMinQty(long qty) {
        setLong(FixProtocol.FIELD_MIN_TRADE_VOL, qty);
        return this;
    }

    public Instrument setMaxQty(long qty) {
        setLong(FixProtocol.FIELD_MAX_QTY_VALUE, qty);
        return this;
    }
*/

    public Instrument setCurrentSession(int session) {
        setInt(FixProtocol.FIELD_CURRENT_SESSION_VALUE, session);
        return this;
    }

    public Instrument setSessionPeriod(int period) {
        setInt(FixProtocol.FIELD_SESSION_PERIOD_VALUE, period);
        return this;
    }

    public Instrument setDevLimitPrcAvg(double price) {
        setDouble(FixProtocol.FIELD_DEV_LIMIT_AVG_PRICE_VALUE, price);
        return this;
    }

/*    public Instrument setDevLimitLastDealPrc(double priceDev) {
        setDouble(FixProtocol.FIELD_DEV_LIMIT_LAST_DEAL_PRC_VALUE, priceDev);
        return this;
    }*/

    public Instrument setDevLimitMarketPrc(double priceDev) {
        setDouble(FixProtocol.FIELD_DEV_LIM_MARKET_PRC_VALUE, priceDev);
        return this;
    }

    public Instrument setDevWarnAvgPrc(double priceDev) {
        setDouble(FixProtocol.FIELD_DEV_WARN_AVG_PRC_VALUE, priceDev);
        return this;
    }

    public Instrument setDevWarnBestBuyPrc(double priceDev) {
        setDouble(FixProtocol.FIELD_DEV_WARN_BEST_BUY_PRC_VALUE, priceDev);
        return this;
    }

    public Instrument setDevWarnBestSellPrc(double priceDev) {
        setDouble(FixProtocol.FIELD_DEV_WARN_BEST_SELL_PRC_VALUE, priceDev);
        return this;
    }

/*
    public Instrument setCrossCurrency(long currency) {
        setLong(FixProtocol.FIELD_CROSS_CURRENCY, currency);
        return this;
    }
*/

    public Instrument setCounterCurrency(long counterCur) {
        setLong(FixProtocol.FIELD_COUNTER_CURRENCY, counterCur);
        return this;
    }


    public Instrument setPriceStep(double step) {
        setDouble(FixProtocol.FIELD_MIN_PRICE_INCREMENT, step);
        return this;
    }

//    public Instrument setOrderTypes(List<String> orderTypes) {
//        StringBuilder stb = new StringBuilder();
//
//        int lastIndex = orderTypes.size() - 1;
//
//        for (String ord : orderTypes) {
//            stb.append(ord);
//            if (orderTypes.indexOf(ord) < lastIndex) {
//                stb.append(",");
//            }
//        }
//        setString(FIELD_ORDER_TYPES, stb.toString());
//        return this;
//    }

/*    public Instrument setOrderSides(List<String> orderSides) {
        StringBuilder stb = new StringBuilder();

        int lastIndex = orderSides.size() - 1;

        for (String s : orderSides) {
            stb.append(s);

            if (orderSides.indexOf(s) < lastIndex) {
                stb.append(",");
            }
        }
        setString(FIELD_TEXT, stb.toString());
        return this;
    }*/
//
//    public Instrument setExpectDateAllowed(boolean flag) {
//        setBoolean(FIELD_EXPECT_DATE_ALLOWED, flag);
//        return this;
//    }

    public Instrument setMaxExpectDateInDays(int days) {
        setInt(FIELD_MAX_EXPECT_DATE_IN_DAYS, days);
        return this;
    }

    public boolean hasLot() {
        return isSetField(FixProtocol.FIELD_LOT_VALUE);
    }

    public boolean hasSymbol() {
        return isSetField(FixProtocol.FIELD_SYMBOL);
    }



    public Instrument setSecurityStatus(SeсurityStatus status) {
        setString(FixProtocol.FIELD_SECURITY_STATUS, Integer.toString(status.getValue()));
        return this;
    }

    public SeсurityStatus getSecurityStatus() {
        return SeсurityStatus.getByValue(getInt(FixProtocol.FIELD_SECURITY_STATUS));

    }

    public boolean hasSecurityStatus() {
        return isSetField(FixProtocol.FIELD_SECURITY_STATUS);
    }


    public Instrument setIssuer(String issuer) {
        setString(FixProtocol.FIELD_ISSUER, issuer);
        return this;
    }

    public String getIssuer() {
        return getString(FixProtocol.FIELD_ISSUER);

    }

    public boolean hasIssuer() {
        return isSetField(FixProtocol.FIELD_ISSUER);
    }


    public Instrument setProduct(Product product) {
        setString(FixProtocol.FIELD_PRODUCT, Integer.toString(product.getValue()));
        return this;
    }

    public Product getProduct() {
        return Product.valueOf(getInt(FixProtocol.FIELD_PRODUCT));

    }

    public boolean hasProduct() {
        return isSetField(FixProtocol.FIELD_PRODUCT);
    }

    public Instrument setCurrency(String currency) {
        setString(FixProtocol.FIELD_CURRENCY, currency);
        return this;
    }

    public String getCurrency() {
        return getString(FixProtocol.FIELD_CURRENCY);
    }

    public boolean hasCurrency() {
        return isSetField(FixProtocol.FIELD_CURRENCY);
    }


    public Instrument setSecurityDescr(String descr) {
        setString(FixProtocol.FIELD_SECURITY_DESC, descr);
        return this;
    }

    public String getSecurityDescr() {
        return getString(FixProtocol.FIELD_SECURITY_DESC);
    }

    public boolean hasSecurityDescr() {
        return isSetField(FixProtocol.FIELD_SECURITY_DESC);
    }

    public Instrument setSecurityGroup(String descr) {
        setString(FixProtocol.FIELD_SECURITY_GROUP, descr);
        return this;
    }

    public String getSecurityGroup() {
        return getString(FixProtocol.FIELD_SECURITY_GROUP);
    }

    public boolean hasSecurityGroup() {
        return isSetField(FixProtocol.FIELD_SECURITY_GROUP);
    }

    public Instrument setCFICode(String cfi) {
        setString(FixProtocol.FIELD_CFI_CODE, cfi);
        return this;
    }

    public boolean hasCFICode() {
        return isSetField(FixProtocol.FIELD_CFI_CODE);
    }

    public String getCFICode() {
        return getString(FixProtocol.FIELD_CFI_CODE);
    }
/*

    public Instrument setTags(String[] tags) {
        int tagsSize = tags.length;
        if (tagsSize == 0) return this;

        StringBuilder stb = new StringBuilder();
        for (int i = 0; i < tagsSize; i++) {
            String tag = tags[i];
            stb.append(tag);

            if (i < tags.length - 1) {
                stb.append(",");
            }
        }

        setString(FIELD_TAGS, stb.toString());
        return this;
    }
*/

    public Instrument setFullName(String fullName) {
        setString(FIELD_SECURITY_DESC, fullName);
        return this;
    }

    public Instrument setIsFuture(boolean isFuture) {
        setBoolean(FixProtocol.FIELD_IS_FUTURE, isFuture);
        return this;
    }

    public boolean isFuture() {
        return getBoolean(FixProtocol.FIELD_IS_FUTURE);
    }

    public boolean hasIsFuture() {
        return isSetField(FixProtocol.FIELD_IS_FUTURE);
    }

    public Instrument setContractMultiplier(long contractMultiplier) {
        setLong(FixProtocol.FIELD_CONTRACT_MULTIPLIER, contractMultiplier);
        return this;
    }

    public Long getContractMultiplier() {
        return getLong(FixProtocol.FIELD_CONTRACT_MULTIPLIER);
    }

    public boolean hasContractMultiplier() {
        return isSetField(FixProtocol.FIELD_CONTRACT_MULTIPLIER);
    }

    public Instrument setUnderlyingInstrId(long underlyingInstrId) {
        setLong(FixProtocol.FIELD_UNDERLYING_INSTR_ID, underlyingInstrId);
        return this;
    }

    public Long getUnderlyingInstrId() {
        return getLong(FixProtocol.FIELD_UNDERLYING_INSTR_ID);
    }

    public boolean hasUnderlyingInstrId() {
        return isSetField(FixProtocol.FIELD_UNDERLYING_INSTR_ID);
    }

    public Instrument setMarginRatio(double marginRatio) {
        setDouble(FixProtocol.FIELD_MARGIN_RATIO, marginRatio);
        return this;
    }

    public double getMarginRatio() {
        return getDouble(FixProtocol.FIELD_MARGIN_RATIO);
    }

    public boolean hasMarginRatio() {
        return isSetField(FixProtocol.FIELD_MARGIN_RATIO);
    }

    public Instrument setMaintenanceMargin(double maintenanceMargin) {
        setDouble(FixProtocol.FIELD_MAINTENANCE_MARGIN, maintenanceMargin);
        return this;
    }

    public double getMaintenanceMargin() {
        return getDouble(FixProtocol.FIELD_MAINTENANCE_MARGIN);
    }

    public boolean hasMaintenanceMargin() {
        return isSetField(FixProtocol.FIELD_MAINTENANCE_MARGIN);
    }
}
