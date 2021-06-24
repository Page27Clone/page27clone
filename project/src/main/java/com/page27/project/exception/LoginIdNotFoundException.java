package com.page27.project.exception;

public class LoginIdNotFoundException extends RuntimeException {
    public LoginIdNotFoundException() {
        super();
    }

    public LoginIdNotFoundException(String s) {
        super(s);
    }

    public LoginIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginIdNotFoundException(Throwable cause) {
        super(cause);
    }

    protected LoginIdNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
