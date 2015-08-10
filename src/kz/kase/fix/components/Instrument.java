package kz.kase.fix.components;

import kz.kase.fix.FixProtocol;
import kz.kase.fix.Product;
import kz.kase.fix.SecStatus;
import kz.kase.fix.messages.PaymentType;
import kz.kase.fix.messages.RedemptionPriceType;
import kz.kase.fix.messages.YieldType;

import java.util.Date;

import static kz.kase.fix.FixProtocol.*;

public class Instrument extends quickfix.MessageComponent {

    private static final int[] componentFields = {
            55, 65, 48, 22, 460, /**/ 1151,/**/ 461,167, 762, 200, 541, 965, 224, 225, 239, 226,
            227, 228, 235, 236, 255, 543, 470, 471, 472, 240, 202, 947, 206, 231, 223, 207,
            106, 348, 349, 107, 350, 351, 691, 667, 696, 742, 697, 698, 875, 876, 873, 874,
            5031, 5032, 5033, 5034, 5035, 5036, 5037, 5038, 5039, 5040,
            5041, 5042, 5043, 5044, 5045, 5046, 5047, 5108, 5109, 5110, 5111, 5112, 5113, 5114,
            5190, 5191, 5192, 5193, 5194, 320, 969, 40746, 40739, 701, 898,1149,1148,1150
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

    public Instrument setPaymentStreamDiscountRateDayCount(PaymentType p) {
        setInt(FIELD_PAYMENT_STREAM_DISCOUNT_RATE_DAY_COUNT, p.getValue());
        return this;
    }

    public PaymentType getPaymentStreamDiscountRateDayCount() {
        return PaymentType.valueOf(getInt(FIELD_PAYMENT_STREAM_DISCOUNT_RATE_DAY_COUNT));
    }

    public Instrument setYieldType(YieldType type) {
        setString(FIELD_YIELD_TYPE, type.name());
        return this;
    }

    public YieldType getYieldType() {
        String yieldStr = getString(FIELD_YIELD_TYPE);
        return YieldType.valueOf(yieldStr);
    }

    public Instrument setYield(double yield) {
        setDouble(FIELD_YIELD, yield);
        return this;

    }

    public Double getYield() {
        return getDouble(FIELD_YIELD);
    }

    public Instrument setYieldRedemptionDate(Date date) {
        setUtcDateOnly(FIELD_YIELD_REDEMPTION_DATE, date);
        return this;
    }

    public Date getYieldRedemptionDate() {
        return getUtcDateOnly(FIELD_YIELD_REDEMPTION_DATE);
    }

    public Instrument setInterestAccrualDate(Date date) {
        setUtcDateOnly(FIELD_TILL_DATE, date);
        return this;
    }

    public Date getInterestAccrualDate() {
        return getUtcDateOnly(FIELD_TILL_DATE);
    }

    public Instrument setAccruedInterestAmt(int amount) {
        setInt(FIELD_ACCRUED_INTEREST_AMT, amount);
        return this;
    }

    public Integer getAccruedInterestAmt() {
        return getInt(FIELD_ACCRUED_INTEREST_AMT);
    }


    public Instrument setIssueDate(Date date) {
        setUtcDateOnly(FIELD_ISSUE_DATE, date);
        return this;
    }

    public Date getIssueDate() {
        return getUtcDateOnly(FIELD_ISSUE_DATE);
    }

    public boolean hasIssueDate() {
        return isSetField(FIELD_ISSUE_DATE);
    }


    public Instrument setYieldCalcDate(Date d) {
        setUtcDateOnly(FIELD_YIELD_CALC_DATE, d);
        return this;
    }

    public Date getYieldCalcDate() {
        return getUtcDateOnly(FIELD_YIELD_CALC_DATE);
    }

    public boolean hasYieldCalcDate() {
        return isSetField(FIELD_YIELD_CALC_DATE);
    }


    public Instrument setAccruedInterestRate(double d) {
        setDouble(FIELD_ACCRUED_INTEREST_RATE, d);
        return this;
    }

    public Double getAccruedInterestRate() {
        return getDouble(FIELD_ACCRUED_INTEREST_RATE);
    }

    public boolean hasAccruedInterestRate() {
        return isSetField(FIELD_ACCRUED_INTEREST_RATE);
    }

    public Instrument setRepurchaseTerm(int i) {
        setInt(FIELD_REPURCHASE_TERM, i);
        return this;
    }

    public Integer getRepurchaseTerm() {
        return getInt(FIELD_REPURCHASE_TERM);
    }

    public boolean hasRepurchaseTerm() {
        return isSetField(FIELD_REPURCHASE_TERM);
    }

    public Instrument setLowLimitPrice(double price) {
        setDouble(FIELD_LOW_LIMIT_PRICE, price);
        return this;
    }

    public Double getLowLimitPrice() {
        return getDouble(FIELD_LOW_LIMIT_PRICE);
    }

    public boolean hasLowLimitPrice() {
        return isSetField(FIELD_LOW_LIMIT_PRICE);
    }
    public Instrument setHighLimitPrice(double price) {
        setDouble(FIELD_HIGH_LIMIT_PRICE, price);
        return this;
    }

    public Double getHighLimitPrice() {
        return getDouble(FIELD_HIGH_LIMIT_PRICE);
    }

    public boolean hasHighLimitPrice() {
        return isSetField(FIELD_HIGH_LIMIT_PRICE);

    }    public Instrument setTradingRefPrice(double price) {
        setDouble(FIELD_TRADING_REFERENCE_PRICE, price);
        return this;
    }

    public Double getTradingRefPrice() {
        return getDouble(FIELD_TRADING_REFERENCE_PRICE);
    }

    public boolean hasTradingRefPrice() {
        return isSetField(FIELD_TRADING_REFERENCE_PRICE);
    }

    public Instrument setYieldRedemptionPriceType(RedemptionPriceType type) {
        setInt(FIELD_YIELD_REDEMPTION_PRICE_TYPE, type.getValue());
        return this;
    }

    public RedemptionPriceType getYieldRedemptionPriceType() {
        return RedemptionPriceType.valueOf(getInt(FIELD_YIELD_REDEMPTION_PRICE_TYPE));
    }

    public Instrument setYieldRedemptionPrice(double price) {
        setDouble(FIELD_YIELD_REDEMPTION_PRICE, price);
        return this;
    }

    public Double getYieldRedemptionPrice() {
        return getDouble(FIELD_YIELD_REDEMPTION_PRICE);
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

    public Instrument setCrossCurrency(String currency) {
        setString(FixProtocol.FIELD_CROSS_CURRENCY, currency);
        return this;
    }

    public Instrument setCounterCurrency(String counterCur) {
        setString(FixProtocol.FIELD_COUNTER_CURRENCY, counterCur);
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


    public Instrument setSecurityStatus(SecStatus status) {
        setString(FixProtocol.FIELD_SECURITY_STATUS, Integer.toString(status.getValue()));
        return this;
    }

    public SecStatus getSecurityStatus() {
        return SecStatus.getByValue(getInt(FixProtocol.FIELD_SECURITY_STATUS));

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

    public Date getMaturityMonthYear() {
        return getUtcDateOnly(FixProtocol.FIELD_MATURITY_MONTH_YEAR);
    }

    public boolean hasMaturityMonthYear() {
        return isSetField(FixProtocol.FIELD_MATURITY_MONTH_YEAR);
    }

    public Instrument setMaturityMonthYear(Date maturityMonthYear) {
        setUtcDateOnly(FixProtocol.FIELD_MATURITY_MONTH_YEAR, maturityMonthYear);
        return this;
    }

    public Date getMaturityDate() {
        return getUtcDateOnly(FixProtocol.FIELD_MATURITY_DATE);
    }

    public boolean hasMaturityDate() {
        return isSetField(FixProtocol.FIELD_MATURITY_DATE);
    }

    public Instrument setMaturityDate(Date maturityDate) {
        setUtcDateOnly(FixProtocol.FIELD_MATURITY_DATE, maturityDate);
        return this;
    }

    public Date getCouponPaymentDate() {
        return getUtcDateOnly(FixProtocol.FIELD_COUPON_PAYMENT_DATE);
    }

    public boolean hasCouponPaymentDate() {
        return isSetField(FixProtocol.FIELD_COUPON_PAYMENT_DATE);
    }

    public Instrument setCouponPaymentDate(Date couponPaymentDate) {
        setUtcDateOnly(FixProtocol.FIELD_COUPON_PAYMENT_DATE, couponPaymentDate);
        return this;
    }

    public double getCouponRate() {
        return getDouble(FixProtocol.FIELD_COUPON_RATE);
    }

    public boolean hasCouponRate() {
        return isSetField(FixProtocol.FIELD_COUPON_RATE);
    }

    public Instrument setCouponRate(double couponRate) {
        setDouble(FixProtocol.FIELD_COUPON_RATE, couponRate);
        return this;
    }

    public double getMaintenanceMargin() {
        return getDouble(FixProtocol.FIELD_MAINTENANCE_MARGIN);
    }

    public boolean hasMaintenanceMargin() {
        return isSetField(FixProtocol.FIELD_MAINTENANCE_MARGIN);
    }
}
