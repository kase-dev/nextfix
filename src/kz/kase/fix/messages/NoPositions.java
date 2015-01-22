package kz.kase.fix.messages;

import kz.kase.fix.PositionType;
import quickfix.Group;

import static kz.kase.fix.FixProtocol.*;


public class NoPositions extends Group {

    public NoPositions() {
        super(FIELD_NO_POSITIONS, FIELD_POSITION_TYPE,
                new int[]{
                        FIELD_POSITION_TYPE, FIELD_LONG_QTY, FIELD_SHORT_QTY,
                        FIELD_POS_QTY_STATUS, FIELD_QTY_DATE
                });
    }

    public NoPositions setPosType(PositionType posType) {
        setString(FIELD_POSITION_TYPE, posType.getValue());
        return this;
    }

}
