package kz.kase.fix;


public enum SeсurityStatus implements IntItem {

    Active(1),
    Inactive(2),;

    private final int value;

    private SeсurityStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static SeсurityStatus getByValue(int i) {
        for (SeсurityStatus v : values()) {
            if (v.getValue() == i) return v;
        }
        return null;
    }
}
