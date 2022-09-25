package sn.youdev.config.error;

public class BanqueNotFoundException extends Exception{
    public BanqueNotFoundException() {
        super();
    }

    public BanqueNotFoundException(String message) {
        super(message);
    }

    public BanqueNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BanqueNotFoundException(Throwable cause) {
        super(cause);
    }

    protected BanqueNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
