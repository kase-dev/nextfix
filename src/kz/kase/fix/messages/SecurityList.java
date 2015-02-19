package kz.kase.fix.messages;

import kz.kase.fix.*;
import kz.kase.fix.components.BaseTradingRules;
import kz.kase.fix.components.Instrument;
import quickfix.Group;
import quickfix.Message;

import java.util.List;

import static kz.kase.fix.FixProtocol.*;

public class SecurityList extends Message {

    public SecurityList() {
        super();
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_SEC_LIST);
    }

    public SecurityList setRef(long ref) {
        setLong(FixProtocol.FIELD_SEC_REQ_ID, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FixProtocol.FIELD_SEC_REQ_ID);
    }

    public boolean hasRef() {
        return isSetField(FixProtocol.FIELD_SEC_REQ_ID);
    }

    public SecurityList setTotNoRelatedSym(int num) {
        setInt(FixProtocol.FIELD_TOT_NO_RELATED_SYM, num);
        return this;
    }

    public SecurityList setNoRelatedSym(int num) {
        setInt(FixProtocol.FIELD_NO_RELATED_SYM, num);
        return this;
    }

    public Integer getTotNoRelatedSym() {
        return getInt(FixProtocol.FIELD_TOT_NO_RELATED_SYM);
    }

    public boolean hasTotNoRelatedSym() {
        return isSetField(FixProtocol.FIELD_TOT_NO_RELATED_SYM);
    }

    public SecurityList setLastFragment(boolean flag) {
        setBoolean(FixProtocol.FIELD_LAST_FRAGMENT, flag);
        return this;
    }

    public Boolean getLastFragment() {
        return getBoolean(FixProtocol.FIELD_LAST_FRAGMENT);
    }

    public boolean hasLastFragment() {
        return isSetField(FixProtocol.FIELD_LAST_FRAGMENT);
    }


    public SecurityList setRespId(long respId) {
        setLong(FixProtocol.FIELD_SEC_RESP_ID, respId);
        return this;
    }

    public Long getRespId() {
        return getLong(FixProtocol.FIELD_SEC_RESP_ID);
    }

    public boolean hasRespId() {
        return isSetField(FixProtocol.FIELD_SEC_RESP_ID);
    }


    public int getInstrumentsCount() {
        List<Group> grps = getGroups(FIELD_NO_RELATED_SYM);
        return grps != null ? grps.size() : 0;
    }

    public SecurityList setSecRequestResult(SecurityRequestResult result) {
        setInt(FixProtocol.FIELD_SEC_REQ_RESULT, result.getValue());
        return this;
    }

    public SecurityRequestResult getSecRequestResult() {
        return SecurityRequestResult.valueOf(getInt(FixProtocol.FIELD_SEC_REQ_RESULT));
    }

    public boolean hasSecRequestResult() {
        return isSetField(FixProtocol.FIELD_SEC_REQ_RESULT);
    }


    public static class NoRelatedSym extends Group {

        public NoRelatedSym() {
            super(FIELD_NO_RELATED_SYM, FIELD_SYMBOL,
                    new int[]{
                            55, 65, 48, 22, 454, 460, 461, 167, 762, 200, 541, 224, 225,
                            239, 226, 227, 228, 255, 543, 470, 471, 472, 240, 202, 947,
                            206, 231, 223, 207, 106, 348, 349, 107, 350, 351, 691, 667,
                            875, 876, 864, 873, 874, 965, 966, 1049, 967, 968, 969, 970,
                            971, 1018, 996, 997, 1079, 668, 869, 870, 913, 914, 915, 918,
                            788, 916, 917, 919, 898, 711, 15, 232, 555, 218, 220, 221,
                            222, 662, 663, 699, 761, 235, 236, 701, 696, 697, 698,
                            827, 354, 355, 0,
                            5031, 5032, 5033, 5034, 5035, 5036, 5037, 5038, 5039, 5040,
                            5041, 5042, 5043, 5044, 5045, 5046, 5047,
                            1309, 336, 625, 5184, 1312, 1210, 1211, 40,
                            562, 1140, 1143, 1245, 561, 423, 58, 1237,1239,59

                    });
        }

        public NoRelatedSym addNoTIFRules(NoTimeInForceRules rules) {
            addGroupRef(rules);
            return this;
        }

        public NoRelatedSym addNoTrSesRules(NoTradingSessionRules rules) {
            addGroupRef(rules);
            return this;
        }

        public NoRelatedSym addNesInstrAttr(NoNestedInstrAttrib attrib) {
            addGroupRef(attrib);
            return this;
        }

        public NoRelatedSym addOrdTypeRules(NoOrdTypeRules rules) {
            addGroupRef(rules);
            return this;
        }

        public NoRelatedSym setInstrument(Instrument instr) {
            setComponent(instr);
            return this;
        }


        public NoRelatedSym setBaseRules(BaseTradingRules baseRules) {
            setComponent(baseRules);
            return this;
        }


        public NoRelatedSym setOrderSides(List<Side> orderSides) {
            StringBuilder stb = new StringBuilder();

            int lastIndex = orderSides.size() - 1;

            for (Side s : orderSides) {
                stb.append(s.toString());
                if (orderSides.indexOf(s) < lastIndex) {
                    stb.append(",");
                }
            }
            setString(FIELD_TEXT, stb.toString());
            return this;
        }

        public NoRelatedSym addLeg(InstrmtLegSecListGrp leg) {
            addGroupRef(leg);
            return this;
        }

        /*Согласно протоколу Fix 5.0SP2 поле Currency должно быть указано в группе а не в компоненте инструмента.*/
        public NoRelatedSym setCurrency(String currency) {
            setString(FixProtocol.FIELD_CURRENCY, currency);
            return this;
        }

        public String getCurrency() {
            return getString(FixProtocol.FIELD_CURRENCY);
        }

        public boolean hasCurrency() {
            return isSetField(FixProtocol.FIELD_CURRENCY);
        }
    }

    // Other SecurityList groups
    public static class InstrmtLegSecListGrp extends Group {

        public InstrmtLegSecListGrp() {
            super(FixProtocol.FIELD_NO_LEGS, FixProtocol.FIELD_LEG_SYMBOL,
                    new int[]{
                            600, 601, 602, 603, 604, 607, 608, 609, 764, 610, 611, 248,
                            249, 250, 251, 252, 253, 257, 599, 596, 597, 598, 254, 612,
                            942, 613, 614, 615, 616, 617, 618, 619, 620, 621, 622, 623,
                            624, 556, 740, 739, 955, 956, 999, 1001, 690, 587, 683, 676,
                            677, 678, 679, 680, 0
                    });
            setLegSymbol("leg");
        }


        public InstrmtLegSecListGrp setLegSymbol(String symbol) {
            setString(FixProtocol.FIELD_LEG_SYMBOL, symbol);
            return this;
        }

        public String getLegSymbol() {
            return getString(FixProtocol.FIELD_LEG_SYMBOL);
        }

        public boolean hasLegSymbol() {
            return isSetField(FixProtocol.FIELD_LEG_SYMBOL);
        }


      /*  public InstrmtLegSecListGrp setLegSecurityId(long secId) {
            setString(FixProtocol.FIELD_LEG_SECURITY_ID, Long.toString(secId));
            return this;
        }

        public long getLegSecurityId() {
            return getInt(FixProtocol.FIELD_LEG_SECURITY_ID);
        }

        public boolean hasLegSecurityId() {
            return isSetField(FixProtocol.FIELD_LEG_SECURITY_ID);
        }
*/

        public InstrmtLegSecListGrp setLegQty(int qty) {
            setInt(FixProtocol.FIELD_LEG_QTY, qty);
            return this;
        }

        public int getLegQty() {
            return getInt(FixProtocol.FIELD_LEG_QTY);
        }

        public boolean hasLegQty() {
            return isSetField(FixProtocol.FIELD_LEG_QTY);
        }

    }

    public static class NoNestedInstrAttrib extends Group {

        public NoNestedInstrAttrib() {
            super(FIELD_NO_NESTED_INSTR_ATTRIB,
                    FIELD_NESTED_INSTR_ATTRIB_TYPE,
                    new int[]{1210, 1211});
        }

        public NoNestedInstrAttrib setNestedType(int type) {
            setInt(FIELD_NESTED_INSTR_ATTRIB_TYPE, type);
            return this;
        }

        public int getNestedType() {
            return getInt(FIELD_NESTED_INSTR_ATTRIB_TYPE);
        }

        public NoNestedInstrAttrib setNestedValue(String val) {
            setString(FIELD_NESTED_INSTR_ATTRIB_VALUE, val);
            return this;
        }

        public String getNestedValue() {
            return getString(FIELD_NESTED_INSTR_ATTRIB_VALUE);
        }
    }

    public static class NoOrdTypeRules extends Group {


        public NoOrdTypeRules() {
            super(FIELD_NO_ORDER_TYPE_RULES, FIELD_ORDER_TYPE, new int[]{40});
        }

        public NoOrdTypeRules setOrderType(OrderType o) {
            setChar(FixProtocol.FIELD_ORDER_TYPE, o.getValue());
            return this;
        }

        public String getOrderType() {
            return getString(FIELD_ORDER_TYPE);
        }

    }

    public static class NoTradingSessionRules extends Group {

        public NoTradingSessionRules() {
            super(FIELD_NO_TRADING_SESSION_RULES, FIELD_TRADE_SESSION_ID,
                    new int[]{
                            336, 625, 5184
                    });
        }

        public NoTradingSessionRules setSessionId(String id) {
            setString(FixProtocol.FIELD_TRADE_SESSION_ID, id);
            return this;
        }

/*
        public NoTradingSessionRules setTradingSessionRules(TradingSessionRules tr) {
            setComponent(tr);
            return this;
        }
*/

        public String getSessionId() {
            return getString(FixProtocol.FIELD_TRADE_SESSION_ID);
        }

        public NoTradingSessionRules setSessionSubId(TradeCondition tc) {
            setString(FixProtocol.FIELD_TRADE_SESSION_SUB_ID, tc.toString());
            return this;
        }

        public String getSessionSubId() {
            return getString(FixProtocol.FIELD_TRADE_SESSION_SUB_ID);
        }


    }

    public static class NoTimeInForceRules extends Group {


        public NoTimeInForceRules() {
            super(FIELD_NO_TIME_IN_FORCE_RULES, FIELD_TIME_IN_FORCE, new int[]{59});
        }

        public NoTimeInForceRules setTimeInForce(TimeInForce t) {
            setChar(FIELD_TIME_IN_FORCE, t.getValue());
            return this;
        }

        public String getTimeInForce() {
            return getString(FIELD_TIME_IN_FORCE);
        }

    }



}
