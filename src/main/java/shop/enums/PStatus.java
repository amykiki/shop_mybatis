package shop.enums;

/**
 * Created by Amysue on 2016/4/7.
 */
public enum PStatus {
    InSale(1), OffSale(-1), All(0);
    private int code;

    private PStatus(int code) {
        this.code = code;
    }


    public int getCode() {
        return code;
    }
}
