package kz.kase.fix.messages;

import quickfix.Group;
import quickfix.Message;

import java.util.List;

import static kz.kase.fix.FixProtocol.*;

public class MMLiabilityList extends Message {

    public MMLiabilityList() {
        super(new int[]{
                FIELD_MM_REQUEST_REF,
                FIELD_MEMBER_NAME,
                FIELD_SYMBOL
        });

        getHeader().setString(FIELD_MESSAGE_TYPE, MESSAGE_MM_LIABILITY);
    }

    public MMLiabilityList setRef(long ref) {
        setLong(FIELD_MM_REQUEST_REF, ref);
        return this;
    }

    public Long getRef() {
        return getLong(FIELD_MM_REQUEST_REF);
    }

    public boolean hasRef() {
        return isSetField(FIELD_MM_REQUEST_REF);
    }

  /*  public MMLiabilityList setMemberId(long id) {
        setLong(FIELD_MM_MEMBER_ID, id);
        return this;
    }

    public Long getMemberId() {
        return getLong(FIELD_MM_MEMBER_ID);
    }

    public boolean hasMemberId() {
        return isSetField(FIELD_MM_MEMBER_ID);
    }*/


    public MMLiabilityList setMemberName(String name) {
        setString(FIELD_MEMBER_NAME, name);
        return this;
    }

    public String getMemberName() {
        return getString(FIELD_MEMBER_NAME);
    }

    public boolean hasMemberId() {
        return isSetField(FIELD_MEMBER_NAME);
    }


    /*  public MMLiabilityList setInstrSymbol(String symbol) {
          setString(FIELD_SYMBOL, symbol);
          return this;
      }

      public String getInstrSymbol() {
          return getString(FIELD_SYMBOL);
      }

      public boolean hasInstrSymbol() {
          return isSetField(FIELD_SYMBOL);
      }

  */
    public int getLiabilityCount() {
        List<Group> grps = getGroups(FIELD_MM_ENTRIES);
        return grps != null ? grps.size() : 0;
    }

    public static class MMLiabilityEntries extends Group {


        public MMLiabilityEntries() {
            super(FIELD_MM_ENTRIES, FIELD_SYMBOL, new int[]{
                    FIELD_SYMBOL,
                    FIELD_MM_MAX_SPREAD,
                    FIELD_MM_MAX_SPREAD_PER,
                    FIELD_MM_MIN_QTY,
                    FIELD_MM_MIN_VOL,
                    FIELD_MM_MAX_LAST_DAY_PRC_DEV
            });
        }

/*
        public MMLiabilityEntries setInstrId(long id) {
            setLong(FIELD_MM_INSTR_ID, id);
            return this;
        }

        public Long getInstrId() {
            return getLong(FIELD_MM_INSTR_ID);
        }

        public boolean hasInstrId() {
            return isSetField(FIELD_MM_INSTR_ID);
        }
*/

        public MMLiabilityEntries setSymbol(String symbol) {
            setString(FIELD_SYMBOL, symbol);
            return this;
        }

        public String getSymbol() {
            return getString(FIELD_SYMBOL);
        }

        public boolean hasSymbol() {
            return isSetField(FIELD_SYMBOL);
        }

        public MMLiabilityEntries setMaxSpread(double spread) {
            setDouble(FIELD_MM_MAX_SPREAD, spread);
            return this;
        }

        public Double getMaxSpread() {
            return getDouble(FIELD_MM_MAX_SPREAD);
        }

        public boolean hasMaxSpread() {
            return isSetField(FIELD_MM_MAX_SPREAD);
        }

        public MMLiabilityEntries setMaxSpreadPercent(double spreadPercent) {
            setDouble(FIELD_MM_MAX_SPREAD_PER, spreadPercent);
            return this;
        }

        public Double getMaxSpreadPercent() {
            return getDouble(FIELD_MM_MAX_SPREAD_PER);
        }

        public boolean hasMaxSpreadPercent() {
            return isSetField(FIELD_MM_MAX_SPREAD_PER);
        }

        public MMLiabilityEntries setMinQty(int qty) {
            setInt(FIELD_MM_MIN_QTY, qty);
            return this;
        }

        public Integer getMinQty() {
            return getInt(FIELD_MM_MIN_QTY);
        }

        public boolean hasMinQty() {
            return isSetField(FIELD_MM_MIN_QTY);
        }

        public MMLiabilityEntries setMinVol(double vol) {
            setDouble(FIELD_MM_MIN_VOL, vol);
            return this;
        }

        public Double getMinVol() {
            return getDouble(FIELD_MM_MIN_VOL);
        }

        public boolean hasMinVol() {
            return isSetField(FIELD_MM_MIN_VOL);
        }

        public MMLiabilityEntries setMaxLastDayPricePercDev(double price) {
            setDouble(FIELD_MM_MAX_LAST_DAY_PRC_DEV, price);
            return this;
        }

        public Double getMaxLastDayPricePercDev() {
            return getDouble(FIELD_MM_MAX_LAST_DAY_PRC_DEV);
        }

        public boolean hasMaxLastDayPricePercDev() {
            return isSetField(FIELD_MM_MAX_LAST_DAY_PRC_DEV);
        }

    }


}
