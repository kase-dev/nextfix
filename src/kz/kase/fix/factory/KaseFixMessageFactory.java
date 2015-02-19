package kz.kase.fix.factory;

import kz.kase.fix.FixProtocol;
import kz.kase.fix.messages.*;
import quickfix.Group;
import quickfix.Message;
import quickfix.MessageFactory;

public class KaseFixMessageFactory implements MessageFactory {


    public Message create(String beginString, String msgType) {

        if (FixProtocol.MESSAGE_LOGON.equals(msgType)) {
            return new Logon();

        } else if (FixProtocol.MESSAGE_EXEC_REPORT.equals(msgType)) {
            return new ExecutionReport(true);

        } else if (FixProtocol.MESSAGE_MD_INC_REFRESH.equals(msgType)) {
            return new MDIncRefresh();

        } else if (FixProtocol.MESSAGE_MD_FULL_REFRESH.equals(msgType)) {
            return new MDFullSnapshotRefresh();

        } else if (FixProtocol.MESSAGE_POS_REPORT.equals(msgType)) {
            return new PositionReport(true);

        } else if (FixProtocol.MESSAGE_DAY_POS_REPORT.equals(msgType)) {
            return new DayPositionReport();

        } else if (FixProtocol.MESSAGE_POS_REQUEST.equals(msgType)) {
            return new PositionRequest(true);

        } else if (FixProtocol.MESSAGE_NEW_ORDER_SINGLE.equals(msgType)) {
            return new NewOrderSingle(true);

        } else if (FixProtocol.MESSAGE_ORDER_CANCEL_REQUEST.equals(msgType)) {
            return new OrderCancelRequest(true);

        } else if (FixProtocol.MESSAGE_SEC_LIST_REQUEST.equals(msgType)) {
            return new SecurityListRequest();

        } else if (FixProtocol.MESSAGE_SEC_LIST.equals(msgType)) {
            return new SecurityList();

        } else if (FixProtocol.MESSAGE_TRADE_CAPT_REP_REQ.equals(msgType)) {
            return new TradeCaptureReportRequest();

        } else if (FixProtocol.MESSAGE_SEC_STAT.equals(msgType)) {
            return new SecurityStatus();

        } else if (FixProtocol.MESSAGE_MARKET_DATA_REQUEST.equals(msgType)) {
            return new MarketDataRequest(true);

        } else if (FixProtocol.MESSAGE_CHART_LIST.equals(msgType)) {
            return new ChartList();
        } else if (FixProtocol.MESSAGE_MM_LIABILITY_REQUEST.equals(msgType)) {
            return new MMLiabilityRequest();

        } else if (FixProtocol.MESSAGE_MM_STAT_REQUEST.equals(msgType)) {
            return new MMStatRequest();

        } else if (FixProtocol.MESSAGE_MM_LIABILITY.equals(msgType)) {
            return new MMLiabilityList();

        } else if (FixProtocol.MESSAGE_MM_STAT.equals(msgType)) {
            return new MMStat();

        } else if (FixProtocol.MESSAGE_INDICATIVE_QUOTE.equals(msgType)) {
            return new IndicativeQuoteList();

        } else if (FixProtocol.MESSAGE_INDICATIVE_QUOTE_REQUEST.equals(msgType)) {
            return new IndicativeQuoteRequest();

        } else if (FixProtocol.MESSAGE_CHART_LIST_REQUEST.equals(msgType)) {
            return new ChartListRequest();

        } else if (FixProtocol.MESSAGE_HEARTBEAT.equals(msgType)) {
            return new Heartbeat();

        } else if (FixProtocol.MESSAGE_TEST_REQUEST.equals(msgType)) {
            return new TestRequest();

        } else if (FixProtocol.MESSAGE_RESEND_REQUEST.equals(msgType)) {
            return new ResendRequest();

        } else if (FixProtocol.MESSAGE_REJECT.equals(msgType)) {
            return new Reject();

        } else if (FixProtocol.MESSAGE_SEQUENCE_RESET.equals(msgType)) {
            return new SequenceReset();

        } else if (FixProtocol.MESSAGE_LOGOUT.equals(msgType)) {
            return new Logout();

        } else if (FixProtocol.MESSAGE_INDICATIVE_QUOTE_INSERT.equals(msgType)) {
            return new InsertIndicativeQuote();

        } else if (FixProtocol.MESSAGE_INDICATIVE_QUOTE_CANCEL.equals(msgType)) {
            return new IndicativeQuoteCancel();

        } else if (FixProtocol.MESSAGE_ARC_POS_REQUEST.equals(msgType)) {
            return new ArcPositionRequest();

        } else if (FixProtocol.MESSAGE_ORDER_STATUS_REQUEST.equals(msgType)) {
            return new OrderStatusRequest();

        } else if (FixProtocol.MESSAGE_POS_TRANSFER_INSTRUCTION.equals(msgType)) {
            return new PositionTransferInstruction();

        }

        System.err.println("Unknown message type: '" + msgType + "'");

        return null;
    }


    public Group create(String beginString, String msgType, int correspondingFieldID) {
        System.out.println();

//        if (FixProtocol.MESSAGE_EXEC_REPORT.equals(msgType)) {
//            switch (correspondingFieldID) {
//                case quickfix.field.NoContraBrokers.FIELD:
//                    return new quickfix.fix44.ExecutionReport.NoContraBrokers();
//
//                case quickfix.field.NoUnderlyings.FIELD:
//                    return new quickfix.fix44.ExecutionReport.NoUnderlyings();
//
//                case quickfix.field.NoUnderlyingSecurityAltID.FIELD:
//                    return new quickfix.fix44.ExecutionReport.NoUnderlyings.NoUnderlyingSecurityAltID();
//
//                case quickfix.field.NoContAmts.FIELD:
//                    return new quickfix.fix44.ExecutionReport.NoContAmts();
//
//                case quickfix.field.NoLegs.FIELD:
//                    return new quickfix.fix44.ExecutionReport.NoLegs();
//
//                case quickfix.field.NoLegSecurityAltID.FIELD:
//                    return new quickfix.fix44.ExecutionReport.NoLegs.NoLegSecurityAltID();
//
//                case quickfix.field.NoLegStipulations.FIELD:
//                    return new quickfix.fix44.ExecutionReport.NoLegs.NoLegStipulations();
//
//                case quickfix.field.NoNestedPartyIDs.FIELD:
//                    return new quickfix.fix44.ExecutionReport.NoLegs.NoNestedPartyIDs();
//
//                case quickfix.field.NoNestedPartySubIDs.FIELD:
//                    return new quickfix.fix44.ExecutionReport.NoLegs.NoNestedPartyIDs.NoNestedPartySubIDs();
//
//                case quickfix.field.NoMiscFees.FIELD:
//                    return new quickfix.fix44.ExecutionReport.NoMiscFees();
//
//                case quickfix.field.NoPartyIDs.FIELD:
//                    return new quickfix.fix44.ExecutionReport.NoPartyIDs();
//
//                case quickfix.field.NoPartySubIDs.FIELD:
//                    return new quickfix.fix44.ExecutionReport.NoPartyIDs.NoPartySubIDs();
//
//                case quickfix.field.NoSecurityAltID.FIELD:
//                    return new quickfix.fix44.ExecutionReport.NoSecurityAltID();
//
//                case quickfix.field.NoEvents.FIELD:
//                    return new quickfix.fix44.ExecutionReport.NoEvents();
//
//                case quickfix.field.NoStipulations.FIELD:
//                    return new quickfix.fix44.ExecutionReport.NoStipulations();
//            }
//        }
//
//
//        if (FixProtocol.MESSAGE_MD_INC_REFRESH.equals(msgType)) {
//            switch (correspondingFieldID) {
//                case quickfix.field.NoMDEntries.FIELD:
//                    return new quickfix.fix44.MarketDataIncrementalRefresh.NoMDEntries();
//
//                case quickfix.field.NoUnderlyings.FIELD:
//                    return new quickfix.fix44.MarketDataIncrementalRefresh.NoMDEntries.NoUnderlyings();
//
//                case quickfix.field.NoUnderlyingSecurityAltID.FIELD:
//                    return new quickfix.fix44.MarketDataIncrementalRefresh.NoMDEntries.NoUnderlyings.NoUnderlyingSecurityAltID();
//
//                case quickfix.field.NoLegs.FIELD:
//                    return new quickfix.fix44.MarketDataIncrementalRefresh.NoMDEntries.NoLegs();
//
//                case quickfix.field.NoLegSecurityAltID.FIELD:
//                    return new quickfix.fix44.MarketDataIncrementalRefresh.NoMDEntries.NoLegs.NoLegSecurityAltID();
//
//                case quickfix.field.NoSecurityAltID.FIELD:
//                    return new quickfix.fix44.MarketDataIncrementalRefresh.NoMDEntries.NoSecurityAltID();
//
//                case quickfix.field.NoEvents.FIELD:
//                    return new quickfix.fix44.MarketDataIncrementalRefresh.NoMDEntries.NoEvents();
//            }
//        }
//
//        if (FixProtocol.MESSAGE_POS_REPORT.equals(msgType)) {
//            switch (correspondingFieldID) {
//                case FixProtocol.FIELD_NO_POSITIONS:
//                    return new NoPosKeys();
//                case quickfix.field.NoLegs.FIELD:
//                    return new quickfix.fix44.PositionReport.NoLegs();
//
//                case quickfix.field.NoLegSecurityAltID.FIELD:
//                    return new quickfix.fix44.PositionReport.NoLegs.NoLegSecurityAltID();
//
//                case quickfix.field.NoUnderlyings.FIELD:
//                    return new quickfix.fix44.PositionReport.NoUnderlyings();
//
//                case quickfix.field.NoUnderlyingSecurityAltID.FIELD:
//                    return new quickfix.fix44.PositionReport.NoUnderlyings.NoUnderlyingSecurityAltID();
//
//                case quickfix.field.NoPartyIDs.FIELD:
//                    return new quickfix.fix44.PositionReport.NoPartyIDs();
//
//                case quickfix.field.NoPartySubIDs.FIELD:
//                    return new quickfix.fix44.PositionReport.NoPartyIDs.NoPartySubIDs();
//
//                case quickfix.field.NoSecurityAltID.FIELD:
//                    return new quickfix.fix44.PositionReport.NoSecurityAltID();
//
//                case quickfix.field.NoEvents.FIELD:
//                    return new quickfix.fix44.PositionReport.NoEvents();
//
//                case quickfix.field.NoPositions.FIELD:
//                    return new quickfix.fix44.PositionReport.NoPositions();
//
//                case quickfix.field.NoNestedPartyIDs.FIELD:
//                    return new quickfix.fix44.PositionReport.NoPositions.NoNestedPartyIDs();
//
//                case quickfix.field.NoNestedPartySubIDs.FIELD:
//                    return new quickfix.fix44.PositionReport.NoPositions.NoNestedPartyIDs.NoNestedPartySubIDs();
//
//                case quickfix.field.NoPosAmt.FIELD:
//                    return new quickfix.fix44.PositionReport.NoPosAmt();
//            }
//        }

//        return fix44Factory.create(beginString, msgType, correspondingFieldID);
        return null;
    }
}
