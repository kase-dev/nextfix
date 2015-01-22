
package quickfix;

import java.util.Date;

public enum FieldType {

    Unknown("UNKNOWN"),
    String("STRING"),
    Char("CHAR"),
    Price("PRICE", Double.class),
    Int("INT", Integer.class),
    Amt("AMT", Double.class),
    Qty("QTY", Double.class),
    Currency("CURRENCY"),
    MultipleValueString("MULTIPLEVALUESTRING"),
    Exchange("EXCHANGE"),
    UtcTimeStamp("UTCTIMESTAMP", Date.class),
    Boolean("BOOLEAN", Boolean.class),
    LocalMktDate("LOCALMKTDATE"),
    Data("DATA"),
    Float("FLOAT", Double.class),
    PriceOffset("PRICEOFFSET", Double.class),
    MonthYear("MONTHYEAR"),
    DayOfMonth("DAYOFMONTH", Integer.class),
    UtcDateOnly("UTCDATEONLY", Date.class),
    UtcDate("UTCDATE", Date.class),
    UtcTimeOnly("UTCTIMEONLY", Date.class),
    Time("TIME"),
    NumInGroup("NUMINGROUP", Integer.class),
    Percentage("PERCENTAGE", Double.class),
    SeqNum("SEQNUM", Integer.class),
    Length("LENGTH", Integer.class),
    Country("COUNTRY");

    private String name;
    private Class<?> javaType;

    private FieldType(String name) {
        this(name, String.class);
    }

    private FieldType(String name, Class<?> javaType) {
        this.javaType = javaType;
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public Class<?> getJavaType() {
        return javaType;
    }

    public static FieldType fromOrdinal(int ordinal) {
        for (FieldType type : values()) {
            if (type.ordinal() == ordinal) {
                return type;
            }
        }
        return FieldType.Unknown;
    }

    public static FieldType fromName(String name) {
        for (FieldType type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return FieldType.Unknown;
    }


    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + getName() + "," + getJavaType() + "," + ordinal() + "]";
    }


}