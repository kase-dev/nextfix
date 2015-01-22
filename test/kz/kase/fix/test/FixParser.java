package kz.kase.fix.test;

import kz.kase.fix.MDEntryType;
import kz.kase.fix.TradeCondition;
import kz.kase.fix.messages.MDFullSnapshotRefresh;
import kz.kase.fix.messages.MDIncRefresh;
import quickfix.Group;

import java.util.*;

import static kz.kase.fix.FixProtocol.*;
import static kz.kase.fix.FixProtocol.FIELD_MD_ENTRY_PRICE;
import static kz.kase.fix.FixProtocol.FIELD_MD_ENTRY_SIZE;


public class FixParser {

    public static Collection<InstrHolder> parse(MDIncRefresh md) {
        Map<String, InstrHolder> result = new TreeMap<String, InstrHolder>();

        List<Group> groups = md.getGroups(FIELD_NO_MD_ENTRIES);

        if (groups != null) {
            for (Group g : groups) {
                if (!g.isSetField(FIELD_SYMBOL)) continue;

                String  symbol = g.getString(FIELD_SYMBOL);

                InstrHolder instr = result.get(symbol);
                if (instr == null) {
                    instr = new InstrHolder();
                    instr.setSymbol(symbol);
                    result.put(symbol, instr);
                }

                if (g.isSetField(FIELD_SYMBOL)) {
                    instr.setSymbol(g.getString(FIELD_SYMBOL));
                }

//                if (g.isSetField(FIELD_MD_PRICE_LEVEL)) {
//                    instr.setMarketDepth(g.getInt(FIELD_MD_PRICE_LEVEL));
//                }
//
                if (g.isSetField(FIELD_TRADE_SESSION_ID)) {
                    instr.setTradeSessionId(g.getString(FIELD_TRADE_SESSION_ID));
                }

                if (g.isSetField(FIELD_TRADE_SESSION_SUB_ID)) {
                    instr.setTradeSessionSubId(g.getString(FIELD_TRADE_SESSION_SUB_ID));
                }

                if (g.isSetField(FIELD_NUMBER_OF_ORDERS)) {
                    instr.setNumberOfOrders(g.getInt(FIELD_NUMBER_OF_ORDERS));
                }

                if (g.isSetField(FIELD_LAST_PX)) {
                    instr.setLastPx(g.getDouble(FIELD_LAST_PX));
                }

                if (g.isSetField(FIELD_NET_CHG_PREV_DAY)) {
                    instr.setNetChgPrevDay(g.getDouble(FIELD_NET_CHG_PREV_DAY));
                }

                if (g.isSetField(FIELD_TRADE_VOLUME)) {
                    instr.setTradeVolume(g.getLong(FIELD_TRADE_VOLUME));
                }

                if (g.isSetField(FIELD_TRADE_CONDITION)) {
                    instr.setTradeCondition(TradeCondition.getByValue(g.getString(FIELD_TRADE_CONDITION)));
                }

                if (g.isSetField(FIELD_PRICE_DELTA)) {
                    instr.setPriceDelta(g.getDouble(FIELD_PRICE_DELTA));
                }

                if (g.isSetField(FIELD_MD_ENTRY_TYPE)) {
                    MDEntryType type = MDEntryType.valueOf(g.getChar(FIELD_MD_ENTRY_TYPE));
                    switch (type) {
                        case BID:
                        case OFFER:
                            long qty = g.getLong(FIELD_MD_ENTRY_SIZE);
                            double price = g.getDouble(FIELD_MD_ENTRY_PRICE);
                            instr.addQoute(new QuoteHolder(price, qty, type));
                            break;
                        case OPENING_PRICE:
                            instr.setOpenPrice(g.getDouble(FIELD_MD_ENTRY_PRICE));
                            instr.setOpenVolume(g.getLong(FIELD_MD_ENTRY_SIZE));
                            break;
                        case CLOSING_PRICE:
                            instr.setClosePrice(g.getDouble(FIELD_MD_ENTRY_PRICE));
                            instr.setCloseVolume(g.getLong(FIELD_MD_ENTRY_SIZE));
                            break;
                        case TRADING_SESSION_HIGH_PRICE:
                            instr.setHighPrice(g.getDouble(FIELD_MD_ENTRY_PRICE));
                            instr.setHighVolume(g.getLong(FIELD_MD_ENTRY_SIZE));
                            break;
                        case TRADING_SESSION_LOW_PRICE:
                            instr.setLowPrice(g.getDouble(FIELD_MD_ENTRY_PRICE));
                            instr.setLowVolume(g.getLong(FIELD_MD_ENTRY_SIZE));
                            break;
                    }
                }

            }

        }

        return result.values();
    }


    public static InstrHolder parse(MDFullSnapshotRefresh md) {

        if (md.isSetField(FIELD_SECURITY_ID)) return null;

        long secId = md.getLong(FIELD_SECURITY_ID);
        InstrHolder instr  = new InstrHolder();
        instr.setId(secId);

        if (md.isSetField(FIELD_SYMBOL)) {
            instr.setSymbol(md.getString(FIELD_SYMBOL));
        }

        List<Group> groups = md.getGroups(FIELD_NO_MD_ENTRIES);

        if (groups != null) {
            for (Group g : groups) {

                if (g.isSetField(FIELD_TRADE_SESSION_ID)) {
                    instr.setTradeSessionId(g.getString(FIELD_TRADE_SESSION_ID));
                }

                if (g.isSetField(FIELD_TRADE_SESSION_SUB_ID)) {
                    instr.setTradeSessionNum(g.getInt(FIELD_TRADE_SESSION_SUB_ID));
                }

                if (g.isSetField(FIELD_NUMBER_OF_ORDERS)) {
                    instr.setNumberOfOrders(g.getInt(FIELD_NUMBER_OF_ORDERS));
                }

                if (g.isSetField(FIELD_LAST_PX)) {
                    instr.setLastPx(g.getDouble(FIELD_LAST_PX));
                }

                if (g.isSetField(FIELD_NET_CHG_PREV_DAY)) {
                    instr.setNetChgPrevDay(g.getDouble(FIELD_NET_CHG_PREV_DAY));
                }

                if (g.isSetField(FIELD_TRADE_VOLUME)) {
                    instr.setTradeVolume(g.getLong(FIELD_TRADE_VOLUME));
                }

                if (g.isSetField(FIELD_TRADE_CONDITION)) {
                    instr.setTradeCondition(TradeCondition.getByValue(g.getString(FIELD_TRADE_CONDITION)));
                }

                if (g.isSetField(FIELD_MD_ENTRY_TYPE)) {
                    MDEntryType type = MDEntryType.valueOf(g.getChar(FIELD_MD_ENTRY_TYPE));
                    switch (type) {
                        case BID:
                        case OFFER:
                            long qty = g.getLong(FIELD_MD_ENTRY_SIZE);
                            double price = g.getDouble(FIELD_MD_ENTRY_PRICE);
                            instr.addQoute(new QuoteHolder(price, qty, type));
                            break;
                        case OPENING_PRICE:
                            instr.setOpenPrice(g.getDouble(FIELD_MD_ENTRY_PRICE));
                            instr.setOpenVolume(g.getLong(FIELD_MD_ENTRY_SIZE));
                            break;
                        case CLOSING_PRICE:
                            instr.setClosePrice(g.getDouble(FIELD_MD_ENTRY_PRICE));
                            instr.setCloseVolume(g.getLong(FIELD_MD_ENTRY_SIZE));
                            break;
                        case TRADING_SESSION_HIGH_PRICE:
                            instr.setHighPrice(g.getDouble(FIELD_MD_ENTRY_PRICE));
                            instr.setHighVolume(g.getLong(FIELD_MD_ENTRY_SIZE));
                            break;
                        case TRADING_SESSION_LOW_PRICE:
                            instr.setLowPrice(g.getDouble(FIELD_MD_ENTRY_PRICE));
                            instr.setLowVolume(g.getLong(FIELD_MD_ENTRY_SIZE));
                            break;
                    }
                }

            }

        }

        return instr;
    }
}
