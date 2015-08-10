package kz.kase.fix;


public enum SecStatus implements IntItem {

    Active(1),
    Inactive(2),
    ;

    private final int value;

    private SecStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static SecStatus getByValue(int i) {
        for (SecStatus v : values()) {
            if (v.getValue() == i) return v;
        }
        return null;
    }
}
