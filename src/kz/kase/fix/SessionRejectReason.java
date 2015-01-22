package kz.kase.fix;

public enum SessionRejectReason implements IntItem {

    INVALID_TAG_NUMBER(0),
    REQUIRED_TAG_MISSING(1),
    SENDINGTIME_ACCURACY_PROBLEM(10),
    INVALID_MSGTYPE(11),
    XML_VALIDATION_ERROR(12),
    TAG_APPEARS_MORE_THAN_ONCE(13),
    TAG_SPECIFIED_OUT_OF_REQUIRED_ORDER(14),
    REPEATING_GROUP_FIELDS_OUT_OF_ORDER(15),
    INCORRECT_NUMINGROUP_COUNT_FOR_REPEATING_GROUP(16),
    NON_DATA_VALUE_INCLUDES_FIELD_DELIMITER(17),
    TAG_NOT_DEFINED_FOR_THIS_MESSAGE_TYPE(2),
    UNDEFINED_TAG(3),
    TAG_SPECIFIED_WITHOUT_A_VALUE(4),
    VALUE_IS_INCORRECT(5),
    INCORRECT_DATA_FORMAT_FOR_VALUE(6),
    DECRYPTION_PROBLEM(7),
    SIGNATURE_PROBLEM(8),
    COMPID_PROBLEM(9),
    OTHER(99);


    private final int value;

    private SessionRejectReason(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static SessionRejectReason valueOf(Integer c) {
        if (c == null) return null;
        for (SessionRejectReason v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
