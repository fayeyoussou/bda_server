package sn.youdev.config.error;

public class EntreeException extends Exception{
    public EntreeException() {
        super();
    }

    public EntreeException(String message) {
        super(message);
    }

    public EntreeException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntreeException(Throwable cause) {
        super(cause);
    }

    protected EntreeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
