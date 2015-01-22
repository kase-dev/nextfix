package kz.kase.fix.messages;

import kz.kase.fix.*;
import quickfix.Group;
import quickfix.Message;

import java.util.Currency;
import java.util.Date;

import static kz.kase.fix.FixProtocol.*;

public class MDFullSnapshotRefresh extends Message {


    public MDFullSnapshotRefresh() {
        super(new int[]{1021, 1173, 264, 262, 31337, 55, 48, 461, 451, 268, 5049, 5050});
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_MD_FULL_REFRESH);
    }

    public MDFullSnapshotRefresh setRef(long ref) {
        setLong(FixProtocol.FIELD_MD_REF, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FixProtocol.FIELD_MD_REF);
    }


    public Date getTradeSessionOpenTime() {
        return getUtcTimeOnly(FixProtocol.FIELD_TRADE_SESSION_OPEN_TIME);
    }

    public boolean hasTradeSessionOpenTime() {
        return isSetField(FixProtocol.FIELD_TRADE_SESSION_OPEN_TIME);
    }

    public MDFullSnapshotRefresh setTradeSessionOpenTime(Date date) {
        setUtcTimeOnly(FixProtocol.FIELD_TRADE_SESSION_OPEN_TIME, date);
        return this;
    }

    public Date getTradeSessionCloseTime() {
        return getUtcTimeOnly(FixProtocol.FIELD_TRADE_SESSION_CLOSE_TIME);
    }

    public boolean hasTradeSessionCloseTime() {
        return isSetField(FixProtocol.FIELD_TRADE_SESSION_CLOSE_TIME);
    }

    public MDFullSnapshotRefresh setTradeSessionCloseTime(Date date) {
        setUtcTimeOnly(FixProtocol.FIELD_TRADE_SESSION_CLOSE_TIME, date);
        return this;
    }

    public MDFullSnapshotRefresh setSecurityId(long instrId) {
        setString(FixProtocol.FIELD_SECURITY_ID, Long.toString(instrId));
        return this;
    }

    public MDFullSnapshotRefresh setSecurityDesc(String instr) {
        setString(FixProtocol.FIELD_SECURITY_DESC, instr);
        return this;
    }

    public MDFullSnapshotRefresh setSymbol(String symbol) {
        setString(FIELD_SYMBOL, symbol);
        return this;
    }

    public static class NoMDEntries extends Group {

        public NoMDEntries() {
            super(FixProtocol.FIELD_NO_MD_ENTRIES, FixProtocol.FIELD_MD_ENTRY_TYPE,
                    new int[]{
                            269, 278, 280, 55, 65, 22, 454, 460, 461, 167,
                            762, 200, 541, 224, 225, 239, 226, 227, 228, 255, 543, 470,
                            471, 472, 240, 202, 947, 206, 231, 223, 207, 106, 348, 349,
                            107, 350, 351, 691, 667, 875, 876, 864, 873, 874, 965, 966,
                            1049, 967, 968, 969, 970, 971, 1018, 996, 997, 1079, 711,
                            555, 291, 292, 270, 423, 15, 271, 272, 273, 274, 275, 336, 625,
                            276, 277, 282, 283, 284, 286, 59, 432, 126, 110, 18, 287, 37,
                            299, 288, 289, 346, 811, 290, 546, 811, 451, 58, 354, 355, 528,
                            1024, 332, 333, /* [ */ 1025, 31 /* ] */, 1020, 63, 64, 1070, 83, 1048, 1026, 1027,
                            1023, 453, 198, 40, 0
                    }
            );
        }


        public NoMDEntries setEntryId(long id) {
            setLong(FIELD_MD_ENTRY_ID, id);
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

        public NoMDEntries setVolume(double volume) {
            setDouble(FIELD_MD_ENTRY_SIZE, volume);
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


        public NoMDEntries setTradeSessionId(long sessionId) {
            setLong(FIELD_TRADE_SESSION_ID, sessionId);
            return this;
        }

        public NoMDEntries setTradeSessionSubId(long sessionSubId) {
            setLong(FIELD_TRADE_SESSION_SUB_ID, sessionSubId);
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

        public NoMDEntries setNetChgPrevDay(double lastDayPrice) {
            setDouble(FIELD_NET_CHG_PREV_DAY, lastDayPrice);
            return this;
        }

        public NoMDEntries setTradeVolume(long volume) {
            setLong(FIELD_TRADE_VOLUME, volume);
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

        public NoMDEntries setTradeCondition(TradeCondition type) {
            setString(FIELD_TRADE_CONDITION, type.getValue());
            return this;
        }

        public NoMDEntries setCurrency(Currency currency) {
            setString(FIELD_CURRENCY, currency.getCurrencyCode());
            return this;
        }

        public NoMDEntries setPriceDelta(double delta) {
            setDouble(FIELD_PRICE_DELTA, delta);
            return this;
        }


    }

}
