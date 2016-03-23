package shop.model;

/**
 * Created by Amysue on 2016/3/16.
 */
public enum Role {
    ADMIN(0), NORMAL(1), ANON(2);
    private int code;

    private Role(int code) {
        this.code = code;
    }


    public int getCode() {
        return code;
    }
}
