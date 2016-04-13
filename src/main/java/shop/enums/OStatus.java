package shop.enums;

/**
 * Created by Amysue on 2016/4/12.
 */
public enum OStatus {
    PAID(1), DELIVERED(2), CONFIRMED(3), FINISHED(4), CANCELED(-1);

    private int code;

    private OStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
