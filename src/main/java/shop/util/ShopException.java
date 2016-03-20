package shop.util;

/**
 * Created by Amysue on 2016/3/18.
 */
public class ShopException extends RuntimeException {
    public ShopException() {
        super();
    }

    public ShopException(String message) {
        super(message);
    }

    public ShopException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShopException(Throwable cause) {
        super(cause);
    }

    protected ShopException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
