package com.dhex.shipping;

public class InvalidArgumentDhexException extends RuntimeException {
    public InvalidArgumentDhexException() {
        super();
    }

    public InvalidArgumentDhexException(String message) {
        super(message);
    }

    public InvalidArgumentDhexException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidArgumentDhexException(Throwable cause) {
        super(cause);
    }

    protected InvalidArgumentDhexException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
