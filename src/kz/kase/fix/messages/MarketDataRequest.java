package kz.kase.fix.messages;

import kz.kase.fix.FixProtocol;
import kz.kase.fix.MDEntryType;
import kz.kase.fix.SubscriptionType;
import kz.kase.fix.components.Instrument;
import quickfix.Group;
import quickfix.Message;

import java.util.ArrayList;
import java.util.List;

import static kz.kase.fix.FixProtocol.*;

public class MarketDataRequest extends Message {

    public MarketDataRequest(boolean parse) {
        super();
        getHeader().setString(FixProtocol.FIELD_MESSAGE_TYPE, FixProtocol.MESSAGE_MARKET_DATA_REQUEST);
        if (!parse) {
            setInt(FixProtocol.FIELD_MARKET_DEPTH, 0);
            setInt(FixProtocol.FIELD_NO_MD_ENTRY_TYPES, 0);
            setInt(FixProtocol.FIELD_NO_RELATED_SYM, 1);

        }
    }

    public MarketDataRequest setRef(long ref) {
        setString(FixProtocol.FIELD_MD_REF, Long.toString(ref));
        return this;
    }

    public Long getRef() {
        return getLong(FixProtocol.FIELD_MD_REF);
    }

    public boolean hasRef() {
        return isSetField(FixProtocol.FIELD_MD_REF);
    }

    public MarketDataRequest setMarketDepth(int depth) {
        setInt(FixProtocol.FIELD_MARKET_DEPTH, depth);
        return this;
    }

    public int getMarketDepth() {
        return getInt(FixProtocol.FIELD_MARKET_DEPTH);
    }

    public boolean hasMarketDepth() {
        return isSetField(FixProtocol.FIELD_MARKET_DEPTH);
    }

    public MarketDataRequest setSubscriptionType(SubscriptionType type) {
        setChar(FixProtocol.FIELD_SUBSCRIPTION_TYPE, type.getValue());
        return this;
    }

    public SubscriptionType getSubscriptionType() {
        return SubscriptionType.valueOf(getChar(FixProtocol.FIELD_SUBSCRIPTION_TYPE));
    }

    public boolean hasSubscriptionType() {
        return isSetField(FixProtocol.FIELD_SUBSCRIPTION_TYPE);
    }

    public MarketDataRequest addInstrument(String symbol) {
        Instrument instr = new Instrument();
        instr.setSymbol(symbol);
        return addInstrument(instr);
    }

/*    public MarketDataRequest addInstrument(Long securityId) {
        Instrument instr = new Instrument();
        instr.setSecurityId(securityId);
        return addInstrument(instr);
    }*/

    public MarketDataRequest addInstrument(Instrument instr) {
        NoRelatedSym group = new NoRelatedSym();
        group.setInstrument(instr);
        addGroup(group);
        return this;
    }


    public List<String> getSymbols() {
        List<Group> groups = getGroups(FIELD_NO_RELATED_SYM);
        if (groups != null) {
            List<String> result = new ArrayList<String>();
            for (Group g : groups) {
                if (g.isSetField(FIELD_SYMBOL)) {
                    result.add(g.getString(FIELD_SYMBOL));
                }
            }
            return result;
        }

        return null;
    }

   /* public List<Long> getSecurityIds() {
        List<Group> groups = getGroups(FIELD_NO_RELATED_SYM);
        if (groups != null) {
            List<Long> result = new ArrayList<Long>();
            for (Group g : groups) {
                if (g.isSetField(FIELD_SECURITY_ID)) {
                    result.add(g.getLong(FIELD_SECURITY_ID));
                }
            }
            return result;
        }

        return null;
    }*/

    public static class NoMDEntryTypes extends Group {

        public NoMDEntryTypes() {
            super(FixProtocol.FIELD_NO_MD_ENTRY_TYPES,
                    FixProtocol.FIELD_MD_ENTRY_TYPE, new int[]{FixProtocol.FIELD_MD_ENTRY_TYPE, 0});
        }

        public NoMDEntryTypes setNoMDEntryType(MDEntryType value) {
            setChar(FixProtocol.FIELD_MD_ENTRY_TYPE, value.getValue());
            return this;
        }

        public MDEntryType getNoMDEntryType() {
            char val = getChar(FixProtocol.FIELD_MD_ENTRY_TYPE);
            return MDEntryType.valueOf(val);
        }

        public boolean hasNoMDEntryType() {
            return isSetField(FixProtocol.FIELD_MD_ENTRY_TYPE);
        }
    }

    public static class NoRelatedSym extends Group {

        public NoRelatedSym() {
            super(FIELD_NO_RELATED_SYM, FIELD_SYMBOL,
                    new int[]{
                            FIELD_SYMBOL, 65, FIELD_SECURITY_ID, 22, 454, 460, 461, 167, 762, 200, 541, 224, 225,
                            239, 226, 227, 228, 255, 543, 470, 471, 472, 240, 202, 947,
                            206, 231, 223, 207, 106, 348, 349, 107, 350, 351, 691, 667,
                            875, 876, 864, 873, 874, 711, 555, 0
                    });
        }

        public NoRelatedSym setInstrument(Instrument instr) {
            setComponent(instr);
            return this;
        }

        public Instrument getInstrument() {
            Instrument instr = new Instrument();
            getComponent(instr);
            return instr;
        }

    }


}
