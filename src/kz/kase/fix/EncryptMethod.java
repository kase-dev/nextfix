package kz.kase.fix;

public enum EncryptMethod implements IntItem {
    NONE_OTHER(0),
    PKCS(1),
    DES(2),
    PKCS_DES(3),
    PGP_DES(4),
    PGP_DES_MD5(5),
    PEM_DES_MD5(6);


    private final int value;

    private EncryptMethod(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static EncryptMethod valueOf(Integer c) {
        if (c == null) return null;
        for (EncryptMethod v : values()) {
            if (v.getValue() == c) return v;
        }
        return null;
    }
}
