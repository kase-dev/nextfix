package kz.kase.fix.components;

import static kz.kase.fix.FixProtocol.*;

public class AttrbGrp extends quickfix.MessageComponent {
    @Override
    protected int[] getFields() {
        return new int[0];
    }

    @Override
    protected int[] getGroupFields() {
        return new int[0];
    }

    public static class NoInstrAttrib extends quickfix.Group {

        public NoInstrAttrib() {
            super(FIELD_NO_INSTR_ATTRIB, FIELD_INSTR_ATTRIB_TYPE, new int[]{871, 872});
        }

        public NoInstrAttrib setInstrAttribType(int type) {
            setInt(FIELD_INSTR_ATTRIB_TYPE, type);
            return this;
        }

        public int getInstrAttibType() {
            return getInt(FIELD_INSTR_ATTRIB_TYPE);
        }

        public NoInstrAttrib setInstrAttribValue(String val) {
            setString(FIELD_INSTR_ATTRIB_VALUE, val);
            return this;
        }

        public String getInstrAttibValue() {
            return getString(FIELD_INSTR_ATTRIB_VALUE);
        }


    }

}
