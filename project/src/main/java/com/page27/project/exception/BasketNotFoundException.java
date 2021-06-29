package com.page27.project.exception;

public class BasketNotFoundException extends RuntimeException {
    public BasketNotFoundException() {
    }

    public BasketNotFoundException(String message) {
        super(message);
    }

    public BasketNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BasketNotFoundException(Throwable cause) {
        super(cause);
    }

    public BasketNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
