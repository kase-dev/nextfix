package kz.kase.fix;

public enum SecurityRequestResult implements IntItem {

    VALID_REQUEST(0),
    INVALID_OR_UNSUPPORTED_REQUEST(1),
    NO_INSTRUMENTS_FOUND_THAT_MATCH_SELECTION_CRITERIA(2),
    NOT_AUTHORIZED_TO_RETRIEVE_INSTRUMENT_DATA(3),
    INSTRUMENT_DATA_TEMPORARILY_UNAVAILABLE(4),
    REQUEST_FOR_INSTRUMENT_DATA_NOT_SUPPORTED(5);

    private final int value;

    private SecurityRequestResult(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static SecurityRequestResult valueOf(Integer c) {
        if (c == null) return null;
        for (SecurityRequestResult v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
