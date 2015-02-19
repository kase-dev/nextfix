package kz.kase.fix.test;

import kz.kase.fix.*;
import kz.kase.fix.components.BaseTradingRules;
import kz.kase.fix.components.Instrument;
import kz.kase.fix.core.FixUtils;
import kz.kase.fix.extend.PosKeyType;
import kz.kase.fix.factory.KaseFixMessageFactory;
import kz.kase.fix.messages.*;
import org.junit.Before;
import org.junit.Test;
import quickfix.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static junit.framework.Assert.*;
import static kz.kase.fix.FixProtocol.*;

public class FixProtocolTest {

    public static final boolean VALIDATE = false;


    public static final String USER_DIR = System.getProperty("user.dir");
    public static final String DICT_HOME = USER_DIR + "/etc/";
    //    public static final String FIX44 = "FIX44.xml";
//    public static final String FIX44 = "FIX44_ext.xml";
    public static final String FIX50 = "FIX50_ext.xml";
    public static final double DELTA = 0.00001;
    public static final String FULL_NAME_INSTR = "full name instr";
    public static final int LOT = 100;


    private DataDictionary dictionary;
    private MessageFactory factory;

    @Before
    public void init() throws ConfigError {
//        dictionary = new DataDictionary(DICT_HOME + "/" + FIX44);
        dictionary = new DataDictionary(DICT_HOME + "/" + FIX50);
        factory = new KaseFixMessageFactory();
    }

    @Test
    public void testNewOrderSingle() throws InvalidMessage, IncorrectTagValue, IncorrectDataFormat {

        long ref = 435;
        long accId = 10;
        long instrId = 10;
        double price = 34.01;
        long qty = 1000;
        final String orderRestr = "8";
        Side side = Side.BUY;
        String symbol = "KZT";
        String accName = "F_000_2";

        String comment = "Tralala";
        double spread = 3.4D;

        double risk = 3.0;

        NewOrderSingle.NoPartyIDs partyIDs = new NewOrderSingle.NoPartyIDs();

        String counter_party = "COUNTER_PARTY";
        partyIDs.setPartyId(counter_party);

        NewOrderSingle.NoStipulations repoRisk = new NewOrderSingle.NoStipulations();

        repoRisk.setStipulationType("PROTECT");
        repoRisk.setStipulationValue(risk);

        NewOrderSingle order = new NewOrderSingle()
                .setRef(ref)
                .setOrderType(OrderType.LIMIT)
                .setPrice(price)
                .setQty(qty)
                .setSide(side)
                .setTimeInForce(TimeInForce.DAY)
                .setComment(comment)
                .setSymbol(symbol)
                .setAccount(accName)
                .setOrderRestrictions(orderRestr)
                .setMarketMakerType(true)
                .setSpread(spread)
                .setDoubleOrderType(true)
                .setPriceType(OrderPriceType.AVERAGE_PRICE);

        order.addGroupRef(partyIDs);
        order.addGroupRef(repoRisk);

        prepareHeaders(order);
        String fixMes = order.toString();
        System.out.println("fixMes: " + fixMes);
        NewOrderSingle restored = (NewOrderSingle) MessageUtils.parse(factory, dictionary, fixMes);
        assertNull(restored.getException() != null ?
                        restored.getException().getMessage() : "",
                restored.getException()
        );


        assertEquals(ref, restored.getRef().longValue());
        assertEquals(price, restored.getPrice());
        assertEquals(qty, restored.getQty().longValue());
        assertEquals(side, restored.getSide());
        assertEquals(TimeInForce.DAY, restored.getTimeInForce());
        assertEquals(comment, restored.getComment());
        assertEquals(accName, restored.getAccount());
        assertEquals(symbol, restored.getSymbol());
        assertEquals(true, restored.isMarketMakerType().booleanValue());
        assertEquals(spread, restored.getSpread());
        assertEquals(true, restored.isDoubleOrderType());
        assertEquals(orderRestr, restored.getOrderRestrictions());
        assertEquals(OrderPriceType.AVERAGE_PRICE, restored.getPriceType().intValue());
        assertEquals(counter_party,
                restored.getGroups(FIELD_NO_PARTY_IDS).get(0).getString(FIELD_PARTY_ID));
        assertEquals(risk,
                restored.getGroups(FIELD_NO_STIPULATIONS).get(0).getDouble(FIELD_STIPULATION_VALUE));

        dictionary.validate(restored);
    }

    @Test
    public void testOrderCancelReq() throws InvalidMessage, IncorrectTagValue, IncorrectDataFormat {
        OrderCancelRequest order = new OrderCancelRequest();
        int orderQty = 1500;
        String symbol = "KZTK";
        long ref = 3434L;
        String orderSerial = "NF_3343453";
        order.setOrderQty(orderQty)
                .setSymbol(symbol)
                .setRef(ref)
                .setOrderSerial(orderSerial);

        prepareHeaders(order);
        String fixMes = order.toString();
        System.out.println("fixMes: " + fixMes);
        OrderCancelRequest restored = (OrderCancelRequest) MessageUtils.parse(factory, dictionary, fixMes);
        assertNull(restored.getException() != null ?
                        restored.getException().getMessage() : "",
                restored.getException()
        );

        assertEquals(symbol, restored.getSymbol());
        assertEquals(orderQty, restored.getOrderQty());
        assertEquals(ref, restored.getRef().longValue());
        assertEquals(orderSerial, restored.geOrderSerial());

        dictionary.validate(restored);
    }

    @Test
    public void testExecReport() throws InvalidMessage, IncorrectTagValue, IncorrectDataFormat {

        ExecType execType = ExecType.CALCULATED;

        String orderSerial = "1212E";
        String sellOrderSerial = "1313E";
        String dealSeraial = "33333D";
        OrderType orderType = OrderType.LIMIT;

        String memberName = "Halik Bank";
        long securityId = 34L;
        long accountId = 37L;
        Side side = Side.BUY;
        double price = 4500.1;
        int qty = 500;
        long leavesQty = 600;

        String accId = "F_00000002";
        String sellAcc = "F_0001";
        String buyAcc = "F_0002";
        String sellUserName = "Joppa";

        OrderStatus orderStatus = OrderStatus.FILLED;
        String accOrdName = "FF_00_22";
        String symbol = "KZTK";
        String timeInForce = "SESSION";
        String operator = "Operator";
        String sesNum = "2";
        String dealType = "REGULAR_DEAL";
        double yield = 5.3;
        double cashQty = 1800.0;
        String orderRestriction = "5";
        int ref = 444;
        String sessionSubId = "2";
        int lastQty = 2455;
        int cumQty = 0;
        double lastPrice = 250.50;
        Date effectiveTime = new Date();
        ExecutionReport report = new ExecutionReport()
                .setExecType(execType)
                .setTimeInForce(TimeInForce.FILL_OR_KILL)
                .setAccount(accId)
                .setTradeSessionId(sesNum)
                .setTradeSessionSubId(sessionSubId)
                .setExecId(dealSeraial)
                .setDealType(dealType)
                .setInstrSymbol(symbol)
                .setOrderId(orderSerial)
                .setSellOrderSerial(sellOrderSerial)
                .setSellAcc(sellAcc)
                .setBuyAcc(buyAcc)
                .setSellUserName(sellUserName)
                .setOrderType(orderType)
                .setOrderStatus(orderStatus)
                .setSide(side)
//                .setAccOrdName(accOrdName)
                .setPrice(price)
                .setMemberName(memberName)
                .setQty(qty)
                .setOrderCashQty(cashQty)
                .setOrderRestrictions(orderRestriction)
                .setYield(yield)
                .setWhoRemoved(operator)
                .setLeavesQty(leavesQty)
                .setCumQty(cumQty)
                .setRef(ref)
                .setLastQty(lastQty)
                .setLastPrice(lastPrice)
                .setUserName("PISIA")
                .setComment("wiiii!")
                .setRemovedTime(new Date())
                .setEffectiveTime(effectiveTime);


        report.setUtcDateOnly(FIELD_SETTLEMENT_DATE, new Date());

        prepareHeaders(report);
        String fixMes = report.toString();
        System.out.println("fixMes: " + fixMes);

        ExecutionReport restored = (ExecutionReport) MessageUtils.parse(factory, dictionary, fixMes);
        assertNull(restored.getException() != null ?
                        restored.getException().getMessage() : "",
                restored.getException()
        );


        assertEquals(lastPrice, restored.getLastPrice().doubleValue());
        assertEquals(TimeInForce.FILL_OR_KILL, TimeInForce.valueOf(restored.getTimeInForce()));
        assertEquals(leavesQty, restored.getLeavesQty().longValue());
        assertEquals(execType, restored.getExecType());
        assertEquals(orderType, restored.getOrderType());
        assertEquals(sesNum, restored.getSessionId());
        assertEquals(sessionSubId, restored.getTradeSessionSubId());
        assertEquals(orderSerial, restored.getOrderId());
        assertEquals(accId, restored.getAccount());

        assertEquals(cumQty, restored.getCumQty().intValue());
        assertEquals(operator, restored.getWhoRemoved());
        assertEquals(dealType, restored.getDealType());
        assertEquals(lastQty, restored.getLastQty().intValue());
        assertEquals(symbol, restored.getInstrSymbol());
        assertEquals(dealSeraial, restored.getExecId());
        assertEquals(sellAcc, restored.getSellAcc());
        assertEquals(buyAcc, restored.getBuyAcc());
        assertEquals(sellOrderSerial, restored.getSellOrderSerial());
        assertEquals(sellUserName, restored.getSellUserName());
        assertEquals(memberName, restored.getMemberName());
//        assertEquals(accOrdName, restored.getAccOrdName());
        assertEquals(orderStatus, restored.getOrderStatus());
        assertEquals("PISIA", restored.getUserName());
        assertEquals(side, restored.getSide());
        assertEquals(price, restored.getPrice());
        assertEquals(qty, restored.getQty().longValue());
        assertEquals("wiiii!", restored.getComment());
        assertEquals(ref, restored.getRef().intValue());
        assertTrue(restored.getRemovedTime().getTime() > cumQty);
        assertEquals(yield, restored.getYield());
        assertEquals(cashQty, restored.getOrderCashQty());
        assertEquals(orderRestriction, restored.getOrderRestrictions());
        dictionary.validate(restored);
    }

    @Test
    public void testOrderStatusRequest() throws InvalidMessage {
        String symbol = "KZTK";

        OrderStatusRequest ordStReq = new OrderStatusRequest();
        ordStReq.setSymbol(symbol);
        Date fromAndTo = new Date();
        long ref = 32332L;

        ordStReq.setFromDate(fromAndTo)
                .setToDate(fromAndTo)
                .setRef(ref);

        prepareHeaders(ordStReq);
        String fixMes = ordStReq.toString();
        System.out.println("fixMes: " + fixMes);

        OrderStatusRequest restored = (OrderStatusRequest) MessageUtils.parse(factory, dictionary, fixMes);
        assertNull(restored.getException() != null ?
                        restored.getException().getMessage() : "",
                restored.getException()
        );

        Calendar cal = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();
        cal.setTime(fromAndTo);
        cal2.setTime(restored.getFromDate());

        int date = cal.get(Calendar.DATE);
        int restDate = cal2.get(Calendar.DATE);

        assertEquals(symbol, restored.getSymbol());
        assertEquals(ref, restored.getRef().longValue());
        assertEquals(date, restDate);

    }

    @Test
    public void testArcPositionRequest() throws InvalidMessage, IncorrectTagValue, IncorrectDataFormat {

        ArcPositionRequest arcPos = new ArcPositionRequest();
        arcPos.setRef(1);
        arcPos.setFromDate(new Date());
        arcPos.setToDate(new Date());

        prepareHeaders(arcPos);
        String fixMess = arcPos.toString();
        System.out.println("fixMess: " + fixMess);

        ArcPositionRequest restored = (ArcPositionRequest) MessageUtils.parse(factory, dictionary, fixMess);
        assertNull(restored.getException() != null ?
                        restored.getException().getMessage() : "",
                restored.getException()
        );

        System.out.println("Date from : " + restored.getUtcDateOnly(FIELD_ARC_POS_DATE_FROM));
        System.out.println("Date to   : " + restored.getUtcDateOnly(FIELD_ARC_POS_DATE_TO));

        dictionary.validate(restored);
    }

    @Test
    public void testSecStatus() throws InvalidMessage, IncorrectTagValue, IncorrectDataFormat {

        SecurityStatus ss = new SecurityStatus();
        String symbol = "KZTK";
        String sessionID = "NF_22";
        TradeCondition opened = TradeCondition.Opened;
        ss.setSymbol(symbol)
                .setTradingSessionID(sessionID)
                .setTradingSessionSubID(opened);


        prepareHeaders(ss);
        String fixMess = ss.toString();
        System.out.println("fixMess: " + fixMess);

        SecurityStatus restored = (SecurityStatus) MessageUtils.parse(factory, dictionary, fixMess);
        assertNull(restored.getException() != null ?
                        restored.getException().getMessage() : "",
                restored.getException()
        );
        dictionary.validate(restored);

        assertEquals(opened, restored.getTrSesSubID());
        assertEquals(symbol, restored.getSymbol());
        assertEquals(sessionID, restored.getTradingSessionID());
    }

    @Test
    public void testPositionTransferInstruction() throws InvalidMessage, IncorrectTagValue, IncorrectDataFormat {

        PositionTransferInstruction positionTransferInstruction
                = new PositionTransferInstruction();

        String account = "0001";
        String symbol = "USDKZT";
        String currency = "USD";
        Double position = 1000000.0;

        positionTransferInstruction.setRef(1);

        PositionTransferInstruction.NoTargetPartyIDs noTargetPartyIDs
                = new PositionTransferInstruction.NoTargetPartyIDs();

        noTargetPartyIDs.setTargetPartyID(account);

        NoPositions noPositions
                = new NoPositions();

        noPositions.setDouble(FIELD_LONG_QTY, position);
        noPositions.setPosType(PositionType.AllocationTradeQty);

        positionTransferInstruction.addGroupRef(noPositions);
        positionTransferInstruction.addGroupRef(noTargetPartyIDs);

        prepareHeaders(positionTransferInstruction);
        String fixMess = positionTransferInstruction.toString();
        System.out.println("fixMess: " + fixMess);

        PositionTransferInstruction restored =
                (PositionTransferInstruction) MessageUtils.parse(factory, dictionary, fixMess);

        assertNull(restored.getException() != null ?
                        restored.getException().getMessage() : "",
                restored.getException()
        );

        dictionary.validate(restored);
    }

    @Test
    public void testMarketDataIncremental()
            throws InvalidMessage, ConfigError, IncorrectTagValue, IncorrectDataFormat, ParseException {
        long ref = 1000;

        String instrSymbol = "KZTK";

        double bidPrice1 = 345.20;
        long bidQty1 = 100;

        double askPrice1 = 337.00;
        long askQty1 = 90;

        double openPrice1 = 341.00;
        long openVol1 = 100004;

        double highPrice1 = 349.90;
        long highVol1 = 5000;

        double lowPrice1 = 330.67;
        long lowVol1 = 45000;

        double closePrice1 = 342.19;
        long closeVol1 = 200100;

        double lastPx = 390.2;

        long volume1 = 38018200;
        double priceDelta = 0.31;

        String date = "2013-12-09";
        String openTime = "10:15:00";
        String closeTime = "11:30:00";
        String sessionId = "NF_23233";
        String sesSubId = "T";


//        long instrId2 = 1451;
//
//        double bidPrice2 = 44.05;
//        int bidQty2 = 150;
//
//        double askPrice2 = 49.91;
//        int askQty2 = 115;
//
//        double openPrice2 = 42.17;
//        long openVol2 = 43010;
//
//        double highPrice2 = 56.94;
//        long highVol2 = 200;
//
//        double lowPrice2 = 36.60;
//        long lowVol2 = 4500;
//
//        double closePrice2 = 45.54;
//        long closeVol2 = 10803;
//
//        long volume2 = 10040120;
//
//        int dealCount = 455;
        String tradeSessionSerial = "Session2222";
        List<String> timeInForces = new ArrayList<String>();
        timeInForces.add("FILL_OR_KILL");
        timeInForces.add("DAY");


        MDIncRefresh md = new MDIncRefresh()
                .setBookType(MDBookType.PriceDepth)
                .setRef(ref)
                .setTimeInForces(timeInForces);

        DateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String dealSerial = "NF_wlmq";
        double chPrevDay = 3.3;
        md.addGroupRef(new MDIncRefresh.NoMDEntries()
                .setAction(MDAction.CHANGE)
                .setSymbol(instrSymbol)
                .setMDSubBookType(MDBookType.TopOfBook)
                .setPriceType(PriceType.Percentage)
                .setTradeSessionId(sessionId)
                .setTradeSessionSubId(sesSubId)
                .setType(MDEntryType.TRADE)
                .setEntryId(dealSerial)
                .setNetChgPrevDay(chPrevDay)
                .setPriceLevel(1));


        md.addGroupRef(new MDIncRefresh.NoMDEntries()
                        .setSymbol(instrSymbol)
                        .setAction(MDAction.CHANGE)
                        .setType(MDEntryType.OPENING_PRICE)
                        .setPrice(openPrice1)
                        .setVolume(openVol1)
                        .setTradeSessionOpenTime(dateParser.parse(date + " " + openTime))
                        .setTradeSessionCloseTime(dateParser.parse(date + " " + closeTime))
                        .setLastDealBeforeTodayAvgPrice(222)
        );

        md.addGroupRef(new MDIncRefresh.NoMDEntries()
                        .setSymbol(instrSymbol)
                        .setAction(MDAction.CHANGE)
                        .setType(MDEntryType.CLOSING_PRICE)
                        .setPrice(closePrice1)
                        .setVolume(closeVol1)
        );

        md.addGroupRef(new MDIncRefresh.NoMDEntries()
                        .setSymbol(instrSymbol)
                        .setAction(MDAction.CHANGE)
                        .setType(MDEntryType.TRADING_SESSION_HIGH_PRICE)
                        .setPrice(highPrice1)
                        .setVolume(highVol1)
        );

        md.addGroupRef(new MDIncRefresh.NoMDEntries()
                        .setSymbol(instrSymbol)
                        .setAction(MDAction.CHANGE)
                        .setType(MDEntryType.TRADING_SESSION_LOW_PRICE)
                        .setPrice(lowPrice1)
                        .setVolume(lowVol1)
        );

        md.addGroupRef(new MDIncRefresh.NoMDEntries()
                .setSymbol(instrSymbol)
                .setAction(MDAction.CHANGE)
                .setType(MDEntryType.BID)
                .setPrice(bidPrice1)
                .setVolume(bidQty1));

        md.addGroupRef(new MDIncRefresh.NoMDEntries()
                .setSymbol(instrSymbol)
                .setAction(MDAction.CHANGE)
                .setType(MDEntryType.OFFER)
                .setPrice(askPrice1)
                .setVolume(askQty1));

        md.addGroupRef(new MDIncRefresh.NoMDEntries()
                .setSymbol(instrSymbol)
                .setAction(MDAction.CHANGE)
                .setLastPx(lastPx));

        md.addGroupRef(new MDIncRefresh.NoMDEntries()
                .setSymbol(instrSymbol)
                .setAction(MDAction.CHANGE)
                .setType(MDEntryType.TRADE_VOLUME)
                .setVolume(volume1));

        md.addGroupRef(new MDIncRefresh.NoMDEntries()
                .setSymbol(instrSymbol)
                .setAction(MDAction.CHANGE)
                .setPriceDelta(priceDelta));

        //---------

//        md.addGroupRef(new MDIncRefresh.NoMDEntries()
//                .setAction(MDAction.CHANGE)
//                .setTradeCondition(TradeCondition.Opened)
//                .setSecurityId(instrId2));
//
//        md.addGroupRef(new MDIncRefresh.NoMDEntries()
//                .setSecurityId(instrId2)
//                .setAction(MDAction.CHANGE)
//                .setType(MDEntryType.OPENING_PRICE)
//                .setPrice(openPrice2)
//                .setVolume(openVol2)
//        );
//
//        md.addGroupRef(new MDIncRefresh.NoMDEntries()
//                .setSecurityId(instrId2)
//                .setAction(MDAction.CHANGE)
//                .setType(MDEntryType.CLOSING_PRICE)
//                .setPrice(closePrice2)
//                .setVolume(closeVol2)
//        );
//
//        md.addGroupRef(new MDIncRefresh.NoMDEntries()
//                .setSecurityId(instrId2)
//                .setAction(MDAction.CHANGE)
//                .setType(MDEntryType.TRADING_SESSION_HIGH_PRICE)
//                .setPrice(highPrice2)
//                .setVolume(highVol2)
//        );
//
//        md.addGroupRef(new MDIncRefresh.NoMDEntries()
//                .setSecurityId(instrId2)
//                .setAction(MDAction.CHANGE)
//                .setType(MDEntryType.TRADING_SESSION_LOW_PRICE)
//                .setPrice(lowPrice2)
//                .setVolume(lowVol2)
//        );
//
//        md.addGroupRef(new MDIncRefresh.NoMDEntries()
//                .setSecurityId(instrId2)
//                .setAction(MDAction.CHANGE)
//                .setType(MDEntryType.BID)
//                .setPrice(bidPrice2)
//                .setVolume(bidQty2));
//
//        md.addGroupRef(new MDIncRefresh.NoMDEntries()
//                .setSecurityId(instrId2)
//                .setAction(MDAction.CHANGE)
//                .setType(MDEntryType.OFFER)
//                .setPrice(askPrice2)
//                .setVolume(askQty2));
//
//        md.addGroupRef(new MDIncRefresh.NoMDEntries()
//                .setSecurityId(instrId2)
//                .setAction(MDAction.CHANGE)
//                .setType(MDEntryType.TRADE_VOLUME)
//                .setVolume(volume2).setDealsCount(dealCount));


        prepareHeaders(md);
        String fixMes = md.toString();
        System.out.println("fixMes: " + fixMes);

//        ------------------------------------------------------

        MDIncRefresh restored = (MDIncRefresh) MessageUtils.parse(factory, dictionary, fixMes);

        assertNull(restored.getException() != null ?
                        restored.getException().getMessage() : "",
                restored.getException()
        );

        Collection<InstrHolder> instrs = FixParser.parse(md);

        assertEquals(ref, restored.getRef().longValue());
        assertEquals(1, instrs.size());

        assertEquals("FILL_OR_KILL,DAY", restored.getTimeInForces());

        Iterator<InstrHolder> iterator = instrs.iterator();
        InstrHolder instr1 = iterator.next();

        List<Group> groups = md.getGroups(FIELD_NO_MD_ENTRIES);

        for (Group g : groups) {
            /*if (g.isSetField(FIELD_MD_ENTRY_TYPE)) {
                MDEntryType type = MDEntryType.valueOf(g.getChar(FIELD_MD_ENTRY_TYPE));
                assertEquals(MDEntryType.TRADE, type);

            }

*/

            if (g.isSetField(FIELD_MD_PRICE_LEVEL)) {
                assertEquals(1, g.getInt(FIELD_MD_PRICE_LEVEL));

            }
            if (g.isSetField(FIELD_NET_CHG_PREV_DAY)) {
                assertEquals(chPrevDay, g.getDouble(FIELD_NET_CHG_PREV_DAY));

            }
            if (g.isSetField(FIELD_MD_ENTRY_ID)) {
                assertEquals(dealSerial, g.getString(FIELD_MD_ENTRY_ID));
            }

            if (g.isSetField(FIELD_MD_SUBBOOK_TYPE)) {
                assertEquals(MDBookType.TopOfBook.getValue(), g.getInt(FIELD_MD_SUBBOOK_TYPE));
            }
            if (g.isSetField(FIELD_PRICE_TYPE)) {
                assertEquals(PriceType.Percentage.getValue(), g.getInt(FIELD_PRICE_TYPE));
            }

            if (g.isSetField(FIELD_TRADE_SESSION_ID)) {
                assertEquals(sessionId, g.getString(FIELD_TRADE_SESSION_ID));
            }
            if (g.isSetField(FIELD_TRADE_SESSION_SUB_ID)) {
                assertEquals(sesSubId, g.getString(FIELD_TRADE_SESSION_SUB_ID));
            }


//            assertNotNull(type);

        }
//        assertEquals(dealCount, restored.getInt(FixProtocol.FIELD_DEALS_COUNT));

        assertEquals(openPrice1, instr1.getOpenPrice());
        assertEquals(openVol1, instr1.getOpenVolume().longValue());
        assertEquals(tradeSessionSerial, instr1.getTradeSessionSerial());
        assertEquals(closePrice1, instr1.getClosePrice());
        assertEquals(closeVol1, instr1.getCloseVolume().longValue());

        assertEquals(highPrice1, instr1.getHighPrice());
        assertEquals(highVol1, instr1.getHighVolume().longValue());

        assertEquals(lowPrice1, instr1.getLowPrice());
        assertEquals(lowVol1, instr1.getLowVolume().longValue());

        assertEquals(priceDelta, instr1.getPriceDelta());

//        assertEquals(volume1, instr1.getTradeVolume().longValue());

        assertNotNull(instr1.getQuotes());
        assertEquals(2, instr1.getQuotes().size());
        assertEquals(bidPrice1, instr1.getQuotes().get(0).getPrice());
        assertEquals(bidQty1, instr1.getQuotes().get(0).getQty());

        assertEquals(askPrice1, instr1.getQuotes().get(1).getPrice());
        assertEquals(askQty1, instr1.getQuotes().get(1).getQty());


        DateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

        //--------------

//        InstrHolder instr2 = iterator.next();
//
//        assertEquals(openPrice2, instr2.getOpenPrice());
//        assertEquals(openVol2, instr2.getOpenVolume().longValue());
//
//        assertEquals(closePrice2, instr2.getClosePrice());
//        assertEquals(closeVol2, instr2.getCloseVolume().longValue());
//
//        assertEquals(highPrice2, instr2.getHighPrice());
//        assertEquals(highVol2, instr2.getHighVolume().longValue());
//
//        assertEquals(lowPrice2, instr2.getLowPrice());
//        assertEquals(lowVol2, instr2.getLowVolume().longValue());
//
////        assertEquals(volume2, instr2.getTradeVolume().longValue());
//
//        assertNotNull(instr2.getQuotes());
//        assertEquals(2, instr2.getQuotes().size());
//        assertEquals(bidPrice2, instr2.getQuotes().get(0).getPrice());
//        assertEquals(bidQty2, instr2.getQuotes().get(0).getQty());
//
//        assertEquals(askPrice2, instr2.getQuotes().get(1).getPrice());
//        assertEquals(askQty2, instr2.getQuotes().get(1).getQty());

        dictionary.validate(restored);
    }


    @Test
    public void testMarketDataSnapshot()
            throws InvalidMessage, ConfigError, IncorrectTagValue, IncorrectDataFormat, ParseException {
        long ref = 1000;

        long instrId1 = 1450;

        double bidPrice1 = 345.20;
        long bidQty1 = 100;

        double askPrice1 = 337.00;
        long askQty1 = 90;

        double openPrice1 = 341.00;
        long openVol1 = 100004;

        double highPrice1 = 349.90;
        long highVol1 = 5000;

        double lowPrice1 = 330.67;
        long lowVol1 = 45000;

        double closePrice1 = 342.19;
        long closeVol1 = 200100;

        double lastPx = 390.2;

        String instrSymbol = "KATK";
        long volume1 = 38018200;

        String date = "2013-12-09";
        String openTime = "10:15:00";
        String closeTime = "11:30:00";


        MDFullSnapshotRefresh md = new MDFullSnapshotRefresh()
//                .setBookType(MDBookType.PriceDepth)
                .setSymbol(instrSymbol)

                .setRef(ref);

        DateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        md.setTradeSessionOpenTime(dateParser.parse(date + " " + openTime));
        md.setTradeSessionCloseTime(dateParser.parse(date + " " + closeTime));


        md.addGroupRef(new MDFullSnapshotRefresh.NoMDEntries()
                        .setType(MDEntryType.OPENING_PRICE)
                        .setPrice(openPrice1)
                        .setVolume(openVol1)
        );

        md.addGroupRef(new MDFullSnapshotRefresh.NoMDEntries()
                        .setType(MDEntryType.CLOSING_PRICE)
                        .setPrice(closePrice1)
                        .setVolume(closeVol1)
        );

        md.addGroupRef(new MDFullSnapshotRefresh.NoMDEntries()
                        .setType(MDEntryType.TRADING_SESSION_HIGH_PRICE)
                        .setPrice(highPrice1)
                        .setVolume(highVol1)
        );

        md.addGroupRef(new MDFullSnapshotRefresh.NoMDEntries()
                        .setType(MDEntryType.TRADING_SESSION_LOW_PRICE)
                        .setPrice(lowPrice1)
                        .setVolume(lowVol1)
        );

        md.addGroupRef(new MDFullSnapshotRefresh.NoMDEntries()
                .setType(MDEntryType.BID)
                .setPrice(bidPrice1)
                .setVolume(bidQty1));

        md.addGroupRef(new MDFullSnapshotRefresh.NoMDEntries()
                .setType(MDEntryType.OFFER)
                .setPrice(askPrice1)
                .setVolume(askQty1));

        md.addGroupRef(new MDFullSnapshotRefresh.NoMDEntries()
                .setLastPx(lastPx));

        md.addGroupRef(new MDFullSnapshotRefresh.NoMDEntries()
                .setType(MDEntryType.TRADE_VOLUME)
                .setVolume(volume1));


        prepareHeaders(md);
        String fixMes = md.toString();
        System.out.println("fixMes: " + fixMes);

//        ------------------------------------------------------

        MDFullSnapshotRefresh restored = (MDFullSnapshotRefresh) MessageUtils.parse(factory, dictionary, fixMes);

        assertNull(restored.getException() != null ?
                        restored.getException().getMessage() : "",
                restored.getException()
        );

        InstrHolder instr = FixParser.parse(md);


        assertEquals(instrSymbol, instr.getSymbol());

        assertEquals(openPrice1, instr.getOpenPrice());
        assertEquals(openVol1, instr.getOpenVolume().longValue());

        assertEquals(closePrice1, instr.getClosePrice());
        assertEquals(closeVol1, instr.getCloseVolume().longValue());

        assertEquals(highPrice1, instr.getHighPrice());
        assertEquals(highVol1, instr.getHighVolume().longValue());

        assertEquals(lowPrice1, instr.getLowPrice());
        assertEquals(lowVol1, instr.getLowVolume().longValue());


        assertNotNull(instr.getQuotes());
        assertEquals(2, instr.getQuotes().size());
        assertEquals(bidPrice1, instr.getQuotes().get(0).getPrice());
        assertEquals(bidQty1, instr.getQuotes().get(0).getQty());

        assertEquals(askPrice1, instr.getQuotes().get(1).getPrice());
        assertEquals(askQty1, instr.getQuotes().get(1).getQty());


        DateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        assertEquals(openTime, timeFormatter.format(md.getTradeSessionOpenTime()));
        assertEquals(closeTime, timeFormatter.format(md.getTradeSessionCloseTime()));


        dictionary.validate(restored);
    }


    @Test
    public void testMarketDataRequest() throws InvalidMessage, IncorrectTagValue, IncorrectDataFormat {
        long ref = 1;

        MarketDataRequest request = new MarketDataRequest(false)
                .setRef(ref)
                .setSubscriptionType(SubscriptionType.SNAPSHOT_AND_UPDATES)
                .addInstrument("CCBN")
                .addInstrument("KKGB");


        prepareHeaders(request);
        String fixMes = request.toString();
        System.out.println("fixMes: " + fixMes);

        MarketDataRequest restored = (MarketDataRequest) MessageUtils.parse(factory, dictionary, fixMes);
        assertNull(restored.getException() != null ? restored.getException().getMessage() : "", restored.getException());
        assertEquals(ref, restored.getRef().longValue());

        List<String> symbols = restored.getSymbols();

        assertEquals(2, symbols.size());
        assertEquals("CCBN", symbols.get(0));
        assertEquals("KKGB", symbols.get(1));

        dictionary.validate(restored);
    }


    @Test
    public void testTradeCaptureReq() throws InvalidMessage, IncorrectTagValue, IncorrectDataFormat {
        String tradeReqId = "56";
        int tradeReqType = 4;
        String symbol = "KZTK";

        Date fromDate = new Date();
        Date tillDate = new Date();
        TradeCaptureReportRequest request = new TradeCaptureReportRequest()
                .setTradeReqId(tradeReqId)
                .setTradeReqType(tradeReqType)
                .setSymbol(symbol)
                .setFromDate(fromDate)
                .setTillDate(tillDate);


        prepareHeaders(request);
        String fixMes = request.toString();
        System.out.println("fixMes: " + fixMes);

        TradeCaptureReportRequest restored = (TradeCaptureReportRequest) MessageUtils.parse(factory, dictionary, fixMes);
        assertNull(restored.getException() != null ?
                restored.getException().getMessage() : "", restored.getException());

        assertEquals(tradeReqId, restored.getReportReqId());
        assertEquals(symbol, restored.getSymbol());
        assertEquals(fromDate.getDay(), restored.getFromDate().getDay());
        assertEquals(tillDate.getDay(), restored.getTillDate().getDay());
        assertEquals(tradeReqType, restored.getReportReqType().intValue());

        dictionary.validate(restored);
    }


    @Test
    public void testPositionRequest() throws InvalidMessage, IncorrectTagValue, IncorrectDataFormat {
        long ref = 1;

        PositionRequest request = new PositionRequest()
                .setRef(ref);

        prepareHeaders(request);
        String fixMes = request.toString();
        System.out.println("fixMes: " + fixMes);

        PositionRequest restored = (PositionRequest) MessageUtils.parse(factory, dictionary, fixMes);
        assertNull(restored.getException().getMessage(), restored.getException());
        assertEquals(ref, restored.getRef().longValue());
        dictionary.validate(restored);
    }


    @Test
    public void testPositionReport() throws InvalidMessage, IncorrectTagValue, IncorrectDataFormat {
        long ref = 1;
        boolean posType = false;
        String accName = "F_000_22";
        String symbol = "KZTK";
        String cur1 = "KZT";
        String cur2 = "USD";
        String cur3 = "EUR";
        double moneyPos = 45000.65;
        double moneyBlocked = 470.18;
        String instr1ExtCode = "Uno";
        double instr1Pos = 100.00;
        String instr2ExtCode = "Duo";
        double instr2Pos = 900.01;
        double instr2Blocked = 30.02;

        String memberName = "Aliance Bank";

        PositionReport report = new PositionReport()
                .setRef(ref)
                .setAccountName(accName)
                .setMoneyCurrent(cur1, moneyPos)
                .setMoneyBlocked(cur2, moneyBlocked)
                .setInstrCurrent(instr1ExtCode, instr1Pos)
                .setInstrCurrent(instr2ExtCode, instr2Pos)
                .setInstrBlocked(instr2ExtCode, instr2Blocked)
                .setMemberName(memberName);

        prepareHeaders(report);
        String fixMes = report.toString();
        System.out.println("fixMes: " + fixMes);

        PositionReport restored = (PositionReport) MessageUtils.parse(factory, dictionary, fixMes);
//        assertNull(restored.getException().getMessage(), restored.getException());

        assertEquals(ref, restored.getRef().longValue());
        List<Group> keys = restored.getGroups(FixProtocol.FIELD_NO_POS_KEYS);

        assertEquals(4, keys.size());

        Group grp0 = keys.get(0);
        Group grp1 = keys.get(1);
        assertEquals(PosKeyType.MONEY, PosKeyType.valueOf(grp0.getInt(FixProtocol.FIELD_POS_KEY_TYPE)));

        assertEquals(PosKeyType.MONEY, PosKeyType.valueOf(grp0.getInt(FixProtocol.FIELD_POS_KEY_TYPE)));

        assertEquals(memberName, restored.getMemberName());


        List<Group> items0 = grp0.getGroups(FixProtocol.FIELD_NO_POS_ITEMS);
        assertEquals(moneyPos, items0.get(0).getDouble(FixProtocol.FIELD_POS_MONEY_ITEM_VALUE));
        List<Group> items1 = grp1.getGroups(FixProtocol.FIELD_NO_POS_ITEMS);
        assertEquals(moneyBlocked, items1.get(0).getDouble(FixProtocol.FIELD_POS_MONEY_ITEM_VALUE));

        assertEquals(accName, restored.getAccountName());

//        Group grp1 = keys.get(1);

//        assertEquals(PosKeyType.INSTRUMENT, PosKeyType.valueOf(grp1.getInt(FixProtocol.FIELD_POS_KEY_TYPE)));
//        assertEquals(instr1Pos, grp1.getCurrent().longValue());
//        assertNull(grp1.getBlocked());
//
//        Group grp2 = keys.get(2);
//
//        assertEquals(PosKeyType.INSTRUMENT, PosKeyType.valueOf(grp2.getInt(FixProtocol.FIELD_POS_KEY_TYPE)));
//        assertEquals(instr2Pos, grp2.getCurrent().longValue());
//        assertEquals(instr2Blocked, grp2.getBlocked().longValue());

        dictionary.validate(restored);
    }

//    @Test
//    public void testPositionReport2() throws InvalidMessage, FieldConvertError, ConfigError, IncorrectTagValue, IncorrectDataFormat {
//        long ref = 1693;
//        long accId = 134;
//        long instrId = 1;
//        long instrPos = 150;
//
//        PositionReport report = new PositionReport()
//                .setRef(ref)
//                .setAccountId(accId)
//                .setInstrBlocked(instrId, instrPos);
//
//        prepareHeaders(report);
//        String fixMes = report.toString();
////        String fixMes = "8=FIX.4.4\u00019=174\u000135=AP\u000134=6\u000149=FixServer\u000152=20121009-10:40:50.780\u000156=FixClient\u00011=134\u0001581=1\u0001702=1\u0001715=0\u0001721=1693\u0001728=0\u0001730=0\u0001731=0\u0001734=0\u0001753=0\u00015025=1\u00015026=2\u00015027=1\u00015024=1\u00015028=1\u00015029=2\u00015030=150\u000110=054\u0001";
//        System.out.println("fixMes: " + fixMes);
//
//        DataDictionary dd = FixUtils.createDictionary(USER_DIR + "/fix-client-config.cfg");
//        PositionReport restored = (PositionReport) MessageUtils.parse(factory, dd, fixMes);
//        assertNull(restored.getException().getMessage(), restored.getException());
//
//        assertEquals(ref, restored.getRef().longValue());
//        assertEquals(accId, restored.getAccountId().longValue());
//        assertEquals(1, restored.getPositions().size());
//
//        Position pos = restored.getPositions().get(0);
//        assertEquals(PosKeyType.INSTRUMENT, pos.getType());
//        assertEquals(instrId, pos.getInstrId().longValue());
//        assertEquals(instrPos, pos.getBlocked().longValue());
//
//        dictionary.validate(restored);
//    }

    @Test
    public void testDayPositionReport() throws InvalidMessage, IncorrectTagValue, IncorrectDataFormat {
        String symbol = "KZTK";
        String accName = "F_001";
        String curName = "KZT";
        long id = 999;
        long ref = 23;
        double buyPos = 500;
        double sellPos = 1000500;
        double moneyBlocked1 = 233;
        double moneyBlocked2 = 2233;
        double nettoPos = 123123;
        Date sttDate = new Date();
        String memberName = "Alfa Bank";

        DayPositionReport report = new DayPositionReport()
                .setRef(ref)
                .setAccount(accName)
                .setMemberName(memberName);


        DayPositionReport.PosDayKey posDayKey = new DayPositionReport.PosDayKey();
        posDayKey.setCurName(curName)
                .setId(id)
                .setBuyPos(buyPos)
                .setSellPos(sellPos)
                .setBuyBlockedPos(moneyBlocked1)
                .setSellBlockedPos(moneyBlocked2)
                .setNetPos(nettoPos)
                .setTPlusN(2)
                .setSettDate(sttDate);

        report.addGroupRef(posDayKey);

        prepareHeaders(report);
        String fixMes = report.toString();
        System.out.println("fixMes: " + fixMes);

        DayPositionReport restored = (DayPositionReport) MessageUtils.parse(factory, dictionary, fixMes);

        assertEquals(restored.getAccount(), accName);
        assertEquals(restored.getMemberName(), memberName);
        assertEquals(restored.getRef().longValue(), ref);
//        assertEquals(restored.getSettDate().getTime(), sttDate.getTime());
        List<Group> poss = restored.getGroups(FixProtocol.FIELD_DAY_POS_KEY);

        for (Group g : poss) {
            assertEquals(g.getString(FIELD_CURRENCY_NAME), curName);
            assertEquals(g.getLong(FixProtocol.FIELD_DAY_POS_ID), id);
        }

        dictionary.validate(restored);
    }

//    @Test
//    public void testPositionReport2() throws InvalidMessage, FieldConvertError, ConfigError, IncorrectTagValue, IncorrectDataFormat {
//        long ref = 1693;
//        long accId = 134;
//        long instrId = 1;
//        long instrPos = 150;
//
//        PositionReport report = new PositionReport()
//                .setRef(ref)
//                .setAccountId(accId)
//                .setInstrBlocked(instrId, instrPos);
//
//        prepareHeaders(report);
//        String fixMes = report.toString();
////        String fixMes = "8=FIX.4.4\u00019=174\u000135=AP\u000134=6\u000149=FixServer\u000152=20121009-10:40:50.780\u000156=FixClient\u00011=134\u0001581=1\u0001702=1\u0001715=0\u0001721=1693\u0001728=0\u0001730=0\u0001731=0\u0001734=0\u0001753=0\u00015025=1\u00015026=2\u00015027=1\u00015024=1\u00015028=1\u00015029=2\u00015030=150\u000110=054\u0001";
//        System.out.println("fixMes: " + fixMes);
//
//        DataDictionary dd = FixUtils.createDictionary(USER_DIR + "/fix-client-config.cfg");
//        PositionReport restored = (PositionReport) MessageUtils.parse(factory, dd, fixMes);
//        assertNull(restored.getException().getMessage(), restored.getException());
//
//        assertEquals(ref, restored.getRef().longValue());
//        assertEquals(accId, restored.getAccountId().longValue());
//        assertEquals(1, restored.getPositions().size());
//
//        Position pos = restored.getPositions().get(0);
//        assertEquals(PosKeyType.INSTRUMENT, pos.getType());
//        assertEquals(instrId, pos.getInstrId().longValue());
//        assertEquals(instrPos, pos.getBlocked().longValue());
//
//        dictionary.validate(restored);
//    }


    @Test
    public void testSecurityList() throws InvalidMessage, IncorrectTagValue, IncorrectDataFormat {
        long ref = 100;
        long respRef = 101;
        int instrCount = 100;
        final String sesId = "2";
        final TradeCondition tcOpennded = TradeCondition.Opened;
        OrderType orderType = OrderType.LIMIT;
        OrderType orderType2 = OrderType.MARKET;


        SecurityList list = new SecurityList();
        list.setRef(ref);
        list.setRespId(respRef);


        SecurityList.NoNestedInstrAttrib nested = new SecurityList.NoNestedInstrAttrib()
                .setNestedType(27)
                .setNestedValue("2");

        SecurityList.NoOrdTypeRules noOrdTypeRules =
                new SecurityList.NoOrdTypeRules().setOrderType(orderType);

        SecurityList.NoOrdTypeRules noOrdTypeRules2 =
                new SecurityList.NoOrdTypeRules().setOrderType(orderType2);

        SecurityList.NoTradingSessionRules tradingRules = new SecurityList.NoTradingSessionRules()
                .setSessionId(sesId)
                .setSessionSubId(tcOpennded);

        SecurityList.NoTimeInForceRules tifRules = new SecurityList.NoTimeInForceRules()
                .setTimeInForce(TimeInForce.FILL_OR_KILL);


        int maxTrVol = 20000;
        int minTrVol = 100;
        double maxPriceVar = 5.5;
        String crossCurr = "KZT";
        String nin = "FFFFF1";
        String nin2 = "NIN:23323";
        Side buySde = Side.BUY, sellSide = Side.SELL;

        List<Side> sides = new ArrayList<>();
        sides.add(buySde);
        sides.add(sellSide);


        Product product = Product.CURRENCY;

        BaseTradingRules baseRules = new BaseTradingRules();
        baseRules.setMaxTradeVol(maxTrVol);

        baseRules.setMinTradeVol(minTrVol);
        baseRules.setRoundLot(LOT);

        baseRules.setMaxPriceVariation(maxPriceVar);
        baseRules.setTradingCurrency(crossCurr);

        String crossCur = "USD";
        String counterCur = "KZT";
        Instrument instr = new Instrument()
                .setProduct(product)
                .setSymbol("I0001")
                .setFullName(FULL_NAME_INSTR)
                .setNin("FFFFF1")
                .setNominal(30000)
                .setDevLimitPrcAvg(5.4)
                .setDevLimitMarketPrc(5.5)
                .setCurrentSession(1)
                .setPriceStep(2.0)
                .setDevLimitPrcAvg(5)
                .setCrossCurrency(crossCur)
                .setCounterCurrency(counterCur);

        list.addGroupRef(new SecurityList.NoRelatedSym()
                        .addNoTIFRules(tifRules)
                        .setInstrument(instr)
                        .addNesInstrAttr(nested)
                        .addNoTrSesRules(tradingRules)
                        .addOrdTypeRules(noOrdTypeRules)
                        .addOrdTypeRules(noOrdTypeRules2)
                        .setOrderSides(sides)
                        .setBaseRules(baseRules)
        );


        for (long i = 2; i <= instrCount; i++) {
            list.addGroupRef(new SecurityList.NoRelatedSym()
                    .setInstrument(new Instrument()
                                    .setNin(nin2)
                                    .setSymbol("I000" + i)
                    ));
        }


//        list.addGroupRef(new SecurityList.NoRelatedSym()
//                .setInstrument(new Instrument()
//                        .setSecurityId(2L)
//                        .setSymbol("I0002")));
//        list.addGroupRef(new SecurityList.NoRelatedSym()
//                .setInstrument(new Instrument()
//                        .setSecurityId(3L)
//                        .setSymbol("I0003")));
//
//        list.addGroupRef(new SecurityList.NoRelatedSym()
//                .setInstrument(new Instrument()
//                        .setSecurityId(4L)
//                        .setSymbol("I0001/I0002")
////                        .setSecurityGroup("forex,swap")
//                )
//                .addLeg(new SecurityList.InstrmtLegSecListGrp()
//                        .setLegSecurityId(1L)
//                        .setLegSymbol("I0001"))
//                .addLeg(new SecurityList.InstrmtLegSecListGrp()
//                        .setLegSecurityId(2L)
//                        .setLegSymbol("I0002"))
//        );

        prepareHeaders(list);
        String fixMes = list.toString();
//        System.out.println("fixMes: " + fixMes);
        System.out.println("fixMes: " + fixMes.replace("\u0001", "\n").replace("=", ":\t"));

        SecurityList restored = (SecurityList) MessageUtils.parse(factory, dictionary, fixMes);

        assertNull(restored.getException() != null ?
                        restored.getException().getMessage() : "",
                restored.getException()
        );

        assertEquals(ref, restored.getRef().longValue());
        assertEquals(respRef, restored.getRespId().longValue());
        assertEquals(instrCount, restored.getInstrumentsCount());
        //group sex &)
        Group noRelaySym = restored.getGroup(1, FixProtocol.FIELD_NO_RELATED_SYM);

        String restoredSides = noRelaySym.getString(FIELD_TEXT);
        String sidesMass[] = restoredSides.split(",");
        assertEquals(buySde.toString(), sidesMass[0]);
        assertEquals(sellSide.toString(), sidesMass[1]);


        assertEquals(2.0, noRelaySym.getDouble(FixProtocol.FIELD_MIN_PRICE_INCREMENT));
        assertEquals(100, noRelaySym.getInt(FixProtocol.FIELD_MIN_TRADE_VOL));

        Group nest = noRelaySym.getGroup(1, FixProtocol.FIELD_NO_NESTED_INSTR_ATTRIB);
        assertEquals(27, nest.getInt(FixProtocol.FIELD_NESTED_INSTR_ATTRIB_TYPE));
        assertEquals("2", nest.getString(FixProtocol.FIELD_NESTED_INSTR_ATTRIB_VALUE));

        Group tradeSesRls = noRelaySym.getGroup(1, FixProtocol.FIELD_NO_TRADING_SESSION_RULES);
        assertEquals(sesId, tradeSesRls.getString(FIELD_TRADE_SESSION_ID));
        assertEquals(tcOpennded, TradeCondition.valueOf(tradeSesRls.getString(FIELD_TRADE_SESSION_SUB_ID)));

        Group trRulesGr = noRelaySym.getGroup(1, FixProtocol.FIELD_NO_ORDER_TYPE_RULES);
        assertEquals(orderType.getValue(), trRulesGr.getChar(FixProtocol.FIELD_ORDER_TYPE));

        Group trRulesGr2 = noRelaySym.getGroup(2, FixProtocol.FIELD_NO_ORDER_TYPE_RULES);
        assertEquals(orderType2.getValue(), trRulesGr2.getChar(FixProtocol.FIELD_ORDER_TYPE));

        Group timeInForceRulesGrp = noRelaySym.getGroup(1, FIELD_NO_TIME_IN_FORCE_RULES);

        assertEquals(TimeInForce.FILL_OR_KILL, TimeInForce.valueOf(timeInForceRulesGrp.getChar(FIELD_TIME_IN_FORCE)));

        assertEquals(maxTrVol, noRelaySym.getInt(FIELD_MAX_TRADE_VOL));
        assertEquals(minTrVol, noRelaySym.getInt(FIELD_MIN_TRADE_VOL));
        assertEquals(maxPriceVar, noRelaySym.getDouble(FIELD_MAX_PRICE_VARIATION));
        assertEquals(crossCurr, noRelaySym.getString(FIELD_TRADING_CURRENCY));
        assertEquals(LOT, noRelaySym.getInt(FIELD_ROUND_LOT));

        assertEquals("I0001", noRelaySym.getString(FixProtocol.FIELD_SYMBOL));
        assertEquals(crossCur, noRelaySym.getString(FIELD_CROSS_CURRENCY));
        assertEquals(counterCur, noRelaySym.getString(FIELD_COUNTER_CURRENCY));
        assertEquals(product.getValue(), noRelaySym.getInt(FIELD_PRODUCT));

        assertEquals(30000, noRelaySym.getInt(FixProtocol.FIELD_NOMINAL_VALUE));
        assertEquals(nin, noRelaySym.getString(FixProtocol.FIELD_SECURITY_ID));

        assertEquals(FULL_NAME_INSTR, noRelaySym.getString(FixProtocol.FIELD_SECURITY_DESC));


        for (int i = 2; i <= 100; i++) {
            Group grpI = restored.getGroup(i, FixProtocol.FIELD_NO_RELATED_SYM);
            assertEquals(nin2, grpI.getString(FixProtocol.FIELD_SECURITY_ID));
            assertEquals("I000" + i, grpI.getString(FixProtocol.FIELD_SYMBOL));
        }


//        assertEquals("Some company inc.", noRelaySym.getString(FixProtocol.FIELD_ISSUER));
//        assertEquals(Product.EQUITY, Product.valueOf(noRelaySym.getInt(FixProtocol.FIELD_PRODUCT)));
//        assertEquals(SeсurityStatus.Active, SeсurityStatus.getByValue(noRelaySym.getInt(FixProtocol.FIELD_SECURITY_STATUS)));
////        assertEquals("KZT", noRelaySym.getString(FixProtocol.FIELD_CURRENCY));
//        assertEquals("Instrument descr", noRelaySym.getString(FixProtocol.FIELD_SECURITY_DESC));
//        assertEquals("forex", noRelaySym.getString(FixProtocol.FIELD_SECURITY_GROUP));
//
//        Group i2 = restored.getGroup(2, FixProtocol.FIELD_NO_RELATED_SYM);
//        assertEquals(2L, i2.getLong(FixProtocol.FIELD_SECURITY_ID));
//        assertEquals("I0002", i2.getString(FixProtocol.FIELD_SYMBOL));
//
//        Group i3 = restored.getGroup(3, FixProtocol.FIELD_NO_RELATED_SYM);
//        assertEquals(3L, i3.getLong(FixProtocol.FIELD_SECURITY_ID));
//        assertEquals("I0003", i3.getString(FixProtocol.FIELD_SYMBOL));
//        assertEquals("forex,swap", i3.getString(FixProtocol.FIELD_SECURITY_GROUP));

        dictionary.validate(restored);
    }


    @Test
    public void testSettingsDDPath() throws ConfigError, FieldConvertError {
        DataDictionary dd = FixUtils.createDictionary(USER_DIR + "/fix-config-test.cfg");

        boolean isGroup = dd.isGroup(FixProtocol.MESSAGE_POS_REPORT, FixProtocol.FIELD_NO_POS_KEYS);

        System.out.println("isGroup = " + isGroup);
        assertTrue(isGroup);
    }


    @Test
    public void testMMLiabilityList() throws InvalidMessage, IncorrectTagValue, IncorrectDataFormat {


        MMLiabilityList mm = new MMLiabilityList();

        String memberName = "Joppik";
        long ref = 23L;
        int instrId = 23;
        double spread = 33.6;
        double spreadPercent = 10.5;
        int qty = 22;
        double minVol = 44.6;
        double priceDev = 33.3;

        String symbol = "KZT";
        mm.setRef(ref);
        mm.setMemberName(memberName);
        mm.addGroupRef(new MMLiabilityList.MMLiabilityEntries()

                .setSymbol(symbol)
                .setMaxSpread(spread)
                .setMaxSpreadPercent(spreadPercent)
                .setMinQty(qty)
                .setMinVol(minVol)
                .setMaxLastDayPricePercDev(priceDev));

        prepareHeaders(mm);
        String fixMes = mm.toString();
        System.out.println("fixMes: " + fixMes);

        MMLiabilityList restored = (MMLiabilityList) MessageUtils.parse(factory, dictionary, fixMes);
        assertNull(restored.getException() != null ?
                        restored.getException().getMessage() : "",
                restored.getException()
        );

//
        assertEquals(memberName, restored.getMemberName());

        List<Group> list = restored.getGroups(FIELD_MM_ENTRIES);


        for (Group me : list) {

            assertEquals(qty, me.getInt(FIELD_MM_MIN_QTY));
            assertEquals(symbol, me.getString(FIELD_SYMBOL));

        }


        dictionary.validate(restored);
    }

    @Test
    public void testMMRequest() throws InvalidMessage, IncorrectTagValue, IncorrectDataFormat {
        MMLiabilityRequest mm = new MMLiabilityRequest();
        mm.setRef(55L);

        prepareHeaders(mm);
        String fixMes = mm.toString();
        System.out.println("fixMes: " + fixMes);

        MMLiabilityRequest restored = (MMLiabilityRequest) MessageUtils.parse(factory, dictionary, fixMes);
        assertNull(restored.getException() != null ?
                        restored.getException().getMessage() : "",
                restored.getException()
        );

//
        assertEquals(55L, restored.getRef().longValue());

        dictionary.validate(restored);
    }

    @Test
    public void testMMStatRequest() throws InvalidMessage, IncorrectTagValue, IncorrectDataFormat {
        MMStatRequest mm = new MMStatRequest();
        mm.setRef(55L);

        prepareHeaders(mm);
        String fixMes = mm.toString();
        System.out.println("fixMes: " + fixMes);

        MMStatRequest restored = (MMStatRequest) MessageUtils.parse(factory, dictionary, fixMes);
        assertNull(restored.getException() != null ?
                        restored.getException().getMessage() : "",
                restored.getException()
        );

//
        assertEquals(55L, restored.getRef().longValue());

        dictionary.validate(restored);
    }

    @Test
    public void testMMStat() throws InvalidMessage, IncorrectTagValue, IncorrectDataFormat {
        String sesSerial = "12323ER";
        String symbol = "KZTK";


        MMStat mm = new MMStat();
        mm.setRef(55L);

        mm.setInstrSymbol(symbol);
        mm.setBuyPrice(400.5);

        mm.addGroupRef(new MMStat.MMWarningEntries()
                        .setSymbol(symbol)
                        .setStartTime(new Date())
                        .setEndTime(new Date())
                        .setType("Maximus")
                        .setClose(true)
        );

        prepareHeaders(mm);
        String fixMes = mm.toString();
        System.out.println("fixMes: " + fixMes);

        MMStat restored = (MMStat) MessageUtils.parse(factory, dictionary, fixMes);
        assertNull(restored.getException() != null ?
                        restored.getException().getMessage() : "",
                restored.getException()
        );


        assertEquals(symbol, restored.getInstrSymbol());

        List<Group> list = restored.getGroups(FIELD_MM_ENTRIES);
        for (Group me : list) {

            assertEquals("Maximus", me.getDouble(FIELD_MM_WARN_TYPE));

        }

        dictionary.validate(restored);
    }


    @Test
    public void testChartList() throws InvalidMessage, IncorrectTagValue, IncorrectDataFormat, ParseException {
        long ref = 1;
        String symbol = "KZTK";


        DateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");

        ChartList chartList = new ChartList();
        chartList.setRef(ref);
        chartList.setSymbol(symbol);
        chartList.setFromDate(dateParser.parse("2010-11-04"));
        chartList.setToDate(dateParser.parse("2013-10-01"));


        chartList.addGroupRef(new ChartList.NoChartEntries()
                        .setDate(dateParser.parse("2010-11-04"))
                        .setOpenPrice(150.1)
                        .setHighPrice(151.0)
                        .setLowPrice(150.1)
                        .setClosePrice(150.9)
                        .setVolume(1000000000)
        );

        chartList.addGroupRef(new ChartList.NoChartEntries()
                        .setDate(dateParser.parse("2010-11-05"))
                        .setOpenPrice(150.9)
                        .setHighPrice(151.5)
                        .setLowPrice(150.7)
                        .setClosePrice(151.2)
                        .setVolume(14250000)
        );


        prepareHeaders(chartList);
        String fixMes = chartList.toString();
        System.out.println("fixMes: " + fixMes);

        ChartList restored = (ChartList) MessageUtils.parse(factory, dictionary, fixMes);
        assertNull(restored.getException() != null ? restored.getException().getMessage() : "", restored.getException());
        assertEquals(ref, restored.getRef().longValue());

        assertEquals(symbol, restored.getSymbol());
        assertEquals("2010-11-03", dateParser.format(restored.getFromDate()));
        assertEquals("2013-09-30", dateParser.format(restored.getToDate()));

        int chartsCount = restored.getChartsCount();
        assertEquals(2, chartsCount);

        List<Group> chartEntries = restored.getGroups(FixProtocol.FIELD_CHART_NO_ENTRIES);

        Group entry1 = chartEntries.get(0);
        assertEquals("2010-11-04", dateParser.format(entry1.getUtcDateOnly(FixProtocol.FIELD_CHART_ENTRY_DATE)));
        assertEquals(150.1, entry1.getDouble(FixProtocol.FIELD_CHART_ENTRY_OPEN_PRICE), DELTA);
        assertEquals(151.0, entry1.getDouble(FixProtocol.FIELD_CHART_ENTRY_HIGH_PRICE), DELTA);
        assertEquals(150.1, entry1.getDouble(FixProtocol.FIELD_CHART_ENTRY_LOW_PRICE), DELTA);
        assertEquals(150.9, entry1.getDouble(FixProtocol.FIELD_CHART_ENTRY_CLOSE_PRICE), DELTA);
        assertEquals(1000000000, entry1.getDouble(FixProtocol.FIELD_CHART_ENTRY_VOLUME), DELTA);

        Group entry2 = chartEntries.get(1);
        assertEquals("2010-11-05", dateParser.format(entry2.getUtcDateOnly(FixProtocol.FIELD_CHART_ENTRY_DATE)));
        assertEquals(150.9, entry2.getDouble(FixProtocol.FIELD_CHART_ENTRY_OPEN_PRICE), DELTA);
        assertEquals(151.5, entry2.getDouble(FixProtocol.FIELD_CHART_ENTRY_HIGH_PRICE), DELTA);
        assertEquals(150.7, entry2.getDouble(FixProtocol.FIELD_CHART_ENTRY_LOW_PRICE), DELTA);
        assertEquals(151.2, entry2.getDouble(FixProtocol.FIELD_CHART_ENTRY_CLOSE_PRICE), DELTA);
        assertEquals(14250000, entry2.getDouble(FixProtocol.FIELD_CHART_ENTRY_VOLUME), DELTA);

        dictionary.validate(restored);
    }

    @Test
    public void testChartListRequest() throws InvalidMessage, IncorrectTagValue, IncorrectDataFormat, ParseException {
        long ref = 1;
        String instrSymbol = "KZTK";


        DateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");

        ChartListRequest request = new ChartListRequest();
        request.setRef(ref);
        request.setSymbol(instrSymbol);
        request.setFromDate(dateParser.parse("2010-11-04"));
        request.setToDate(dateParser.parse("2013-10-01"));


        prepareHeaders(request);
        String fixMes = request.toString();
        System.out.println("fixMes: " + fixMes);

        ChartListRequest restored = (ChartListRequest) MessageUtils.parse(factory, dictionary, fixMes);
        assertNull(restored.getException() != null ? restored.getException().getMessage() : "", restored.getException());
        assertEquals(ref, restored.getRef().longValue());

        assertEquals(ref, restored.getRef().longValue());
        assertEquals(instrSymbol, restored.getSymbol());
        assertEquals("2010-11-03", dateParser.format(restored.getFromDate()));
        assertEquals("2013-09-30", dateParser.format(restored.getToDate()));
        dictionary.validate(restored);
    }

    public static int mesSeqNum = 0;


    //
    private static void prepareHeaders(Message mes) {
//        String beginString = "FIX.4.4";
        String beginString = "FIXT.1.1";
        String senderCompID = "FixClient";
        String senderSubID = SessionID.NOT_SET;
        String senderLocationID = SessionID.NOT_SET;
        String targetCompID = "FixServer";
        String targetSubID = SessionID.NOT_SET;
        String targetLocationID = SessionID.NOT_SET;

        FixUtils.initializeHeader(mes.getHeader(),
                beginString,
                senderCompID, senderSubID, senderLocationID,
                targetCompID, targetSubID, targetLocationID,
                mesSeqNum++);
    }


}
