package kz.kase.fix.messages;

import kz.kase.fix.*;
import kz.kase.fix.extend.NoPosKeys;
import kz.kase.fix.extend.PosItemType;
import kz.kase.fix.extend.PosKeyType;
import quickfix.Group;
import quickfix.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kz.kase.fix.FixProtocol.*;

public class PositionReport extends Message {

    //    private NoPosKeys moneyGroup;
    private Map<String, NoPosKeys> moneyMap;
    private Map<String, NoPosKeys> posMap;

//    private List<Position> positions;
//    private boolean posParsed = false;

    public PositionReport() {
        this(false);
    }

    public PositionReport(boolean parse) {
        super();
        getHeader().setString(FIELD_MESSAGE_TYPE, MESSAGE_POS_REPORT);
        if (!parse) {
            PosReqResult status = PosReqResult.VALID_REQUEST;
            setInt(FIELD_POS_REQ_RESULT, status.getValue());
            setInt(FIELD_POS_SETTL_PRICE, 0);
            setInt(FIELD_POS_SETTL_PRICE_TYPE, SettlPriceType.Final.getValue());
            setInt(FIELD_CLEARING_BUSINESS_DAY, 0);
            setInt(FIELD_ACCOUNT_TYPE, AccountType.HOUSE_TRADER.getValue());
            setInt(FIELD_POS_PRIOR_SETTL_PRICE, 0);
            setInt(FIELD_NO_POS_AMOUNT, 0);
            addGroup(new NoPositions().setPosType(PositionType.AllocationTradeQty));
        }
    }

    public PositionReport setRef(long ref) {
        setLong(FIELD_POS_MAINT_RPT_ID, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FIELD_POS_MAINT_RPT_ID);
    }

    public boolean hasRef() {
        return isSetField(FIELD_POS_MAINT_RPT_ID);
    }


    public PositionReport setAccountId(long accountId) {
        setLong(FIELD_ACCOUNT, accountId);
        return this;
    }
/*
    public Long getAccountId() {
        return getLong(FIELD_ACCOUNT);
    }

    public boolean hasAccountId() {
        return isSetField(FIELD_ACCOUNT);
    }*/

/*    public PositionReport setInstrSymbol(String symbol) {
        setString(FIELD_SYMBOL, symbol);
        return this;
    }

    public String getInstrSymbol() {
        return getString(FIELD_SYMBOL);
    }

    public boolean hasInstrSymbol() {
        return isSetField(FIELD_SYMBOL);
    }*/

    public PositionReport setAccountName(String account) {
        setString(FIELD_TEXT, account);
        return this;
    }

    public String getAccountName() {
        return getString(FIELD_TEXT);
    }

    public boolean hasAccountName() {
        return isSetField(FIELD_TEXT);
    }

    public PositionReport setMarketType(Product product) {
        setString(FixProtocol.FIELD_PRODUCT, Integer.toString(product.getValue()));
        return this;
    }

    public Product getMarketType() {
        return Product.valueOf(getInt(FixProtocol.FIELD_PRODUCT));
    }

    public boolean hasMarketType() {
        return isSetField(FixProtocol.FIELD_PRODUCT);
    }

    public PositionReport setMoneyInitial(String currName, double val) {
        getOrCreateMoneyGroup(currName).addMoneyItem(PosItemType.INITIAL, val);
        return this;
    }

    public PositionReport setMoneyCurrent(String currName, double val) {
        getOrCreateMoneyGroup(currName).addMoneyItem(PosItemType.CURRENT, val);
        return this;
    }

    public PositionReport setMoneyBlocked(String currName, double val) {
        getOrCreateMoneyGroup(currName).addMoneyItem(PosItemType.BLOCKED, val);
        return this;
    }

    public PositionReport setMoneyPlanned(String currName, double val) {
        getOrCreateMoneyGroup(currName).addMoneyItem(PosItemType.PLANNED, val);
        return this;
    }


//    public PositionReport setInstrInitial(String  extCode, long val) {
//        getOrCreatePosGrp(extCode).addItem(PosItemType.INITIAL, val);
//        return this;
//    }

    public PositionReport setMemberName(String name) {
        setString(FIELD_MEMBER_NAME, name);
        return this;
    }

    public String getMemberName() {
        return getString(FIELD_MEMBER_NAME);
    }

    public boolean hasMemberName() {
        return isSetField(FIELD_MEMBER_NAME);
    }

    public boolean hasInstrInitial(String execCode) {
        NoPosKeys np = posMap.get(execCode);
        if (np == null) {
            return false;
        }
        List<Group> items = np.getGroups(FIELD_NO_POS_ITEMS);

        for (Group item : items) {
            PosItemType type = PosItemType.valueOf(item.getInt(FIELD_POS_ITEM_TYPE));
            if (type.equals(PosItemType.INITIAL)) {
                return true;
            }
        }
        return false;
    }

    public PositionReport setInstrCurrent(String extCode, double val) {
        getOrCreatePosGrp(extCode).addItem(PosItemType.CURRENT, val);
        return this;
    }

    public PositionReport setInstrBlocked(String extCode, double val) {
        getOrCreatePosGrp(extCode).addItem(PosItemType.BLOCKED, val);
        return this;
    }

    public PositionReport setInstrPlanned(String extCode, double val) {
        getOrCreatePosGrp(extCode).addItem(PosItemType.PLANNED, val);
        return this;
    }


    public Integer getNoPositions() {
        return getInt(FIELD_NO_POSITIONS);
    }


//    public List<Position> getPositions() {
//        if (!posParsed) {
//            parsePositions();
//            posParsed = true;
//        }
//        return positions;
//    }


    /*private NoPosKeys getOrCreateMoneyGroup() {
        if (moneyGroup == null) {
            moneyGroup = new NoPosKeys(PosKeyType.MONEY);
            addGroupRef(moneyGroup);
        }
        return moneyGroup;
    }*/

    private NoPosKeys getOrCreateMoneyGroup(String curName) {
        NoPosKeys posKey = moneyMap != null ? moneyMap.get(curName) : null;

        if (posKey == null) {
            posKey = new NoPosKeys(PosKeyType.MONEY, curName);
            addGroupRef(posKey);

            if (moneyMap == null) {
                moneyMap = new HashMap<String, NoPosKeys>();
            }
            moneyMap.put(curName, posKey);
        }

        return posKey;
    }

    private NoPosKeys getMoneyGrp(String curName) {
        return moneyMap == null ? null : moneyMap.get(curName);
    }

    private NoPosKeys getPosGrp(String extCode) {
        return posMap == null ? null : posMap.get(extCode);
    }

    private NoPosKeys getOrCreatePosGrp(String extCode) {
        NoPosKeys posKey = posMap != null ? posMap.get(extCode) : null;

        if (posKey == null) {
            posKey = new NoPosKeys(PosKeyType.INSTRUMENT, extCode);
            addGroupRef(posKey);

            if (posMap == null) {
                posMap = new HashMap<String, NoPosKeys>();
            }
            posMap.put(extCode, posKey);
        }

        return posKey;
    }


//    private void parsePositions() {
//
//        List<Group> keys = getGroups(FIELD_NO_POS_KEYS);
//        if (keys != null) {
//            List<Position> result = new ArrayList<Position>();
//            for (Group key : keys) {
//
//                PosKeyType keyType = PosKeyType.valueOf(key.getInt(FIELD_POS_KEY_TYPE));
//                Long keyValue = null;
//                if (keyType == PosKeyType.INSTRUMENT) {
//                    keyValue = key.getLong(FIELD_POS_KEY_VALUE);
//                }
//
//                Position pos = new Position(keyType, keyValue);
//
//                List<Group> items = key.getGroups(FIELD_NO_POS_ITEMS);
//                if (items != null) {
//                    for (Group item : items) {
//                        PosItemType type = PosItemType.valueOf(item.getInt(FIELD_POS_ITEM_TYPE));
//                        long value = item.getLong(FIELD_POS_ITEM_VALUE);
//
//                        if (PosItemType.CURRENT.equals(type)) {
//                            pos.setCurrent(value);
//                        } else if (PosItemType.BLOCKED.equals(type)) {
//                            pos.setBlocked(value);
//                        } else if (PosItemType.INITIAL.equals(type)) {
//                            pos.setInitial(value);
//                        } else if (PosItemType.PLANNED.equals(type)) {
//                            pos.setPlanned(value);
//                        }
//                    }
//                }
//
//                result.add(pos);
//            }
//
//            positions = result;
//        }
//
//    }



}
