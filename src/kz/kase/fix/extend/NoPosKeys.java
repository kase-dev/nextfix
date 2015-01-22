package kz.kase.fix.extend;

import kz.kase.fix.FixProtocol;
import kz.kase.fix.PosUpdateAction;
import quickfix.Group;

import static kz.kase.fix.FixProtocol.FIELD_POS_ITEM_TYPE;
import static kz.kase.fix.FixProtocol.FIELD_POS_ITEM_VALUE;
import static kz.kase.fix.FixProtocol.FIELD_POS_MONEY_ITEM_VALUE;


public class NoPosKeys extends Group {

    public NoPosKeys() {
        this(false, null, null);
    }

    public NoPosKeys(PosKeyType type) {
        this(false, type, null);
    }

    public NoPosKeys(PosKeyType type, String extCode) {
        this(false, type, extCode);
    }


    public NoPosKeys(boolean parse, PosKeyType type, String extCode) {
        super(FixProtocol.FIELD_NO_POS_KEYS, FixProtocol.FIELD_POS_UPDATE_ACTION,
                new int[]{
                        FixProtocol.FIELD_POS_KEY_TYPE,
                        FixProtocol.FIELD_POS_KEY_VALUE
                });
        if (!parse) {
            if (type != null) {
                setInt(FixProtocol.FIELD_POS_KEY_TYPE, type.getValue());
            }
            if (extCode != null) {
                setString(FixProtocol.FIELD_POS_KEY_VALUE, extCode);
            }
            PosUpdateAction action = PosUpdateAction.CHANGE;
            setInt(FixProtocol.FIELD_POS_UPDATE_ACTION, action.getValue());
        }
    }


    public void addItem(PosItemType type, double value) {
        NoPosItems item = new NoPosItems();
        item.setInt(FIELD_POS_ITEM_TYPE, type.getValue());
        item.setDouble(FIELD_POS_ITEM_VALUE, value);
        addGroup(item);
    }

    public void addMoneyItem(PosItemType type, double value) {
        NoPosItems item = new NoPosItems();
        item.setInt(FIELD_POS_ITEM_TYPE, type.getValue());
        item.setDouble(FIELD_POS_MONEY_ITEM_VALUE, value);
        addGroup(item);
    }


    public static class NoPosItems extends Group {

        public NoPosItems() {
            super(FixProtocol.FIELD_NO_POS_ITEMS, FIELD_POS_ITEM_TYPE,
                    new int[]{
                            FIELD_POS_ITEM_TYPE,
                            FIELD_POS_ITEM_VALUE
                    });
        }

    }
}
