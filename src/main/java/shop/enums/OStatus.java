package shop.enums;

/**
 * Created by Amysue on 2016/4/12.
 */
public enum OStatus {
    BUY(1), PAID(2), DELIVERED(3), CONFIRMED(4);

    private int code;

    private OStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
