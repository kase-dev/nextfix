package kz.kase.fix.messages;

import kz.kase.fix.*;
import quickfix.Group;
import quickfix.Message;

import java.util.Currency;
import java.util.Date;
import java.util.List;

import static kz.kase.fix.FixProtocol.*;

public class MDIncRefresh extends Message {

    private static final int[] order = new int[]{1021, 1022, 75, 262, 31337, 268, 5186};

    public MDIncRefresh() {
        super(order);
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_MD_INC_REFRESH);
    }

    public MDIncRefresh setRef(long ref) {
        setLong(FixProtocol.FIELD_MD_REF, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FixProtocol.FIELD_MD_REF);
    }

    public MDIncRefresh setMDReqId(long reqRef) {
        setLong(FixProtocol.FIELD_MD_REF, reqRef);
        return this;
    }

    public Long getMDReqId() {
        return getLong(FixProtocol.FIELD_MD_REF);
    }

    public boolean hasMDReqId() {
        return isSetField(FixProtocol.FIELD_MD_REF);
    }

    public MDIncRefresh setTimeInForces(List<String> timeInForces) {
        StringBuilder stb = new StringBuilder();

        int lastIndex = timeInForces.size() - 1;

        for (String ord : timeInForces) {
            stb.append(ord);
            if (timeInForces.indexOf(ord) < lastIndex) {
                stb.append(",");
            }
        }
        setString(FIELD_NEXT_TIME_IN_FORCE, stb.toString());
        return this;
    }

    public String getTimeInForces() {
        return getString(FIELD_NEXT_TIME_IN_FORCE);
    }

    public boolean hasTimeInForces() {
        return isSetField(FIELD_NEXT_TIME_IN_FORCE);
    }

    public MDIncRefresh setBookType(MDBookType type) {
        setInt(FixProtocol.FIELD_MD_BOOK_TYPE, type.getValue());
        return this;
    }

    public MDBookType getBookType() {
        return MDBookType.valueOf(getInt(FixProtocol.FIELD_MD_BOOK_TYPE));
    }

    public boolean hasBookType() {
        return isSetField(FixProtocol.FIELD_MD_BOOK_TYPE);
    }


    public static class NoMDEntries extends Group {

        public NoMDEntries() {
            super(FixProtocol.FIELD_NO_MD_ENTRIES, FixProtocol.FIELD_UPDATE_ACTION,
                    new int[]{
                            279, 1173, 264, 285, 269, 278, 280, 55, 65, 48, 22, 454, 460, 461, 167,
                            762, 200, 541, 224, 225, 239, 226, 227, 228, 255, 543, 470,
                            471, 472, 240, 202, 947, 206, 231, 223, 207, 106, 348, 349,
                            107, 350, 351, 691, 667, 875, 876, 864, 873, 874, 965, 966,
                            1049, 967, 968, 969, 970, 971, 1018, 996, 997, 1079, 711,
                            555, 291, 292, 270, 15, 271, 272, 273, 274, 275, 336, 625,
                            276, 277, 282, 283, 284, 286, 59, 432, 126, 110, 18, 287, 37,
                            299, 288, 289, 346, 290, 546, 811, 451, 5106, 5107, 58, 354, 355, 528,
                            1024, 332, 333, /* [ */ 1025, 31, 32 /* ] */, 1020, 63, 64, 1070, 83, 1048, 1026, 1027,
                            1023, 453, 198, 40, 5067, 5068, 5069, 5070, 5071, 5072, 5073, 5074, 5115,
                            5116, 5117, 5118, 5119, 5120, 5157, 5184, 5049, 5050, 5201, 5202, 5203, 0, 423,5205
                    }
            );
        }

        public NoMDEntries setAction(MDAction action) {
            setChar(FixProtocol.FIELD_UPDATE_ACTION, action.getValue());
            return this;
        }

        public NoMDEntries setMDSubBookType(MDBookType bookType) {
            setInt(FixProtocol.FIELD_MD_SUBBOOK_TYPE, bookType.getValue());
            return this;
        }

        public NoMDEntries setEntryId(String id) {
            setString(FIELD_MD_ENTRY_ID, id);
            return this;
        }

        public NoMDEntries setType(MDEntryType type) {
            setChar(FIELD_MD_ENTRY_TYPE, type.getValue());
            return this;
        }

        public NoMDEntries setPrice(double price) {
            setDouble(FIELD_MD_ENTRY_PRICE, price);
            return this;
        }

        public NoMDEntries setPriceType(PriceType price) {
            setInt(FIELD_PRICE_TYPE, price.getValue());
            return this;
        }

        public NoMDEntries setVolume(double volume) {
            setDouble(FIELD_MD_ENTRY_SIZE, volume);
            return this;
        }

        public NoMDEntries setAveragePrice(double price) {
            setDouble(FIELD_AVERAGE_PRC, price);
            return this;
        }

        public NoMDEntries setAveragePricePrev(double price) {
            setDouble(FIELD_AVG_PRC_PREV, price);
            return this;
        }

        public NoMDEntries setOpenedPos(double pos) {
            setDouble(FIELD_OPENED_POS, pos);
            return this;
        }

        public NoMDEntries setLastDealDate(Date date) {
            setUtcTimeStamp(FIELD_LAST_DEAL_DATE, date);
            return this;
        }

        public double getAveragePrice(double price) {
            return getDouble(FIELD_AVERAGE_PRC);
        }

        public double getAveragePricePrev(double price) {
            return getDouble(FIELD_AVG_PRC_PREV);
        }

        public double getOpenedPos(double pos) {
            return getDouble(FIELD_OPENED_POS);
        }

        public Date getTradeSessionOpenTime() {
            return getUtcTimeStamp(FixProtocol.FIELD_TRADE_SESSION_OPEN_TIME);
        }

        public boolean hasTradeSessionOpenTime() {
            return isSetField(FixProtocol.FIELD_TRADE_SESSION_OPEN_TIME);
        }

        public NoMDEntries setTradeSessionOpenTime(Date date) {
            setUtcTimeStamp(FixProtocol.FIELD_TRADE_SESSION_OPEN_TIME, date);
            return this;
        }

        public Date getTradeSessionCloseTime() {
            return getUtcTimeStamp(FixProtocol.FIELD_TRADE_SESSION_CLOSE_TIME);
        }

        public boolean hasTradeSessionCloseTime() {
            return isSetField(FixProtocol.FIELD_TRADE_SESSION_CLOSE_TIME);
        }

        public NoMDEntries setTradeSessionCloseTime(Date date) {
            setUtcTimeStamp(FixProtocol.FIELD_TRADE_SESSION_CLOSE_TIME, date);
            return this;
        }


/*
        public NoMDEntries setSecurityId(long instrId) {
            setString(FixProtocol.FIELD_SECURITY_ID, Long.toString(instrId));
            return this;
        }
*/

        public NoMDEntries setSecurityDesc(String instr) {
            setString(FixProtocol.FIELD_SECURITY_DESC, instr);
            return this;
        }

        public NoMDEntries setSymbol(String symbol) {
            setString(FIELD_SYMBOL, symbol);
            return this;
        }

        public NoMDEntries setPriceLevel(int level) {
            setInt(FIELD_MD_PRICE_LEVEL, level);
            return this;
        }

        public NoMDEntries setEntryPosition(int pos) {
            setDouble(FIELD_MD_ENTRY_POS, pos);
            return this;
        }

        public NoMDEntries setEntryDate(Date date) {
            setUtcTimeStamp(FIELD_MD_ENTRY_DATE, date);
            return this;
        }

        public NoMDEntries setEntryTime(Date date) {
            setUtcTimeStamp(FIELD_MD_ENTRY_TIME, date);
            return this;
        }


        public NoMDEntries setTradeSessionId(String sessionId) {
            setString(FIELD_TRADE_SESSION_ID, sessionId);
            return this;
        }

        public NoMDEntries setTradeSessionSubId(String sessionSubId) {
            setString(FIELD_TRADE_SESSION_SUB_ID, sessionSubId);
            return this;
        }

        public NoMDEntries setNumberOfOrders(int ordersCount) {
            setLong(FIELD_NUMBER_OF_ORDERS, ordersCount);
            return this;
        }

        public NoMDEntries setLastPx(double lastPrice) {
            setDouble(FIELD_LAST_PX, lastPrice);
            return this;
        }

        public NoMDEntries setLastQty(double lastQty) {
            setDouble(FIELD_LAST_QTY, lastQty);
            return this;
        }

        public NoMDEntries setDealsCount(int count) {
            setInt(FIELD_DEALS_COUNT, count);
            return this;
        }

        public NoMDEntries setDealsQtyTotal(String count) {
            setString(FIELD_DEALS_QTY_TOTAL, count);
            return this;
        }

        public NoMDEntries setDealsVolume(double vol) {
            setDouble(FIELD_DEALS_VOLUME, vol);
            return this;
        }

        public NoMDEntries setNetChgPrevDay(double lastDayPrice) {
            setDouble(FIELD_NET_CHG_PREV_DAY, lastDayPrice);
            return this;
        }

        public NoMDEntries setLastDealBeforeTodayPrice(double lastDayPrice) {
            setDouble(FIELD_LAST_DEAL_BEFORE_TODAY_PRICE, lastDayPrice);
            return this;
        }

        public NoMDEntries setLastDealBeforeTodayVolume(double lastDayVol) {
            setDouble(FIELD_LAST_DEAL_BEFORE_TODAY_VOLUME, lastDayVol);
            return this;
        }

        public NoMDEntries setLastDealBeforeTodayAvgPrice(double lastAvgPrice) {
            setDouble(FIELD_LAST_DAY_AVG_PRICE, lastAvgPrice);
            return this;
        }

        public NoMDEntries setTradeVolume(double volume) {
            setDouble(FIELD_TRADE_VOLUME, volume);
            return this;
        }

        public NoMDEntries setSettlType(SettlType type) {
            setString(FIELD_SETTLEMENT_TYPE, type.getValue());
            return this;
        }

        public NoMDEntries setSettlDate(Date date) {
            setUtcTimeOnly(FIELD_SETTLEMENT_DATE, date);
            return this;
        }

        public NoMDEntries setSpotRate(double rate) {
            setDouble(FIELD_MD_ENTRY_SPOT_RATE, rate);
            return this;
        }

        public NoMDEntries setQuoteType(MDQuoteType type) {
            setInt(FIELD_MD_QUOTE_TYPE, type.getValue());
            return this;
        }

//        public NoMDEntries setTradeCondition(TradeCondition type) {
//            setString(FIELD_TRADE_CONDITION, type.getValue());
//            return this;
//        }

        public NoMDEntries setCurrency(Currency currency) {
            setString(FIELD_CURRENCY, currency.getCurrencyCode());
            return this;
        }

        public NoMDEntries setPriceDelta(double delta) {
            setDouble(FIELD_PRICE_DELTA, delta);
            return this;
        }

        public NoMDEntries setLastDealBeforeTime(long time) {
            setUtcDateOnly(FIELD_LAST_DEAL_BEFORE_TODAY_TIME, new Date(time));
            return this;
        }

        public NoMDEntries setLastAverageWeightedPrice(double price) {
            setDouble(FIELD_AVERAGE_WEIGHTED_PRICE, price);
            return this;
        }

        public NoMDEntries setOrdersCount(int qnt) {
            setInt(FIELD_ORDERS_COUNT, qnt);
            return this;
        }

        public NoMDEntries setMembersDealsQty(int qty) {
            setInt(FIELD_MEMBERS_DEALS_QTY, qty);
            return this;
        }

        public NoMDEntries setMembersOrdersQty(int qty) {
            setInt(FIELD_MEMBERS_ORDERS_QTY, qty);
            return this;
        }

        public NoMDEntries setRefSesId(long serial) {
            setLong(FIELD_REF_SES_ID, serial);
            return this;
        }

        public Long getRefSesId() {
            return getLong(FIELD_REF_SES_ID);
        }

        public boolean hasRefSesId() {
            return isSetField(FIELD_REF_SES_ID);
        }
    }

}
