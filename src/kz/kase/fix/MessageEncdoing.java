package kz.kase.fix;


public enum MessageEncdoing implements StringItem {

    ISO_2022_JP("ISO-2022-JP"),
    EUC_JP("EUC-JP"),
    SHIFT_JIS("SHIFT_JIS"),
    UTF_8("UTF_8");

    private final String value;

    private MessageEncdoing(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

//    public static MessageEncdoing valueOf(String c) {
//        for (MessageEncdoing v : values()) {
//            if (v.getValue() == c) return v;
//        }
//        return null;
//    }
}
