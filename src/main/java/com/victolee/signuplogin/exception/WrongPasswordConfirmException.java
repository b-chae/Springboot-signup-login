package com.victolee.signuplogin.exception;

public class WrongPasswordConfirmException extends RuntimeException{
    public WrongPasswordConfirmException() {
        super();
    }

    public WrongPasswordConfirmException(String message) {
        super(message);
    }

    public WrongPasswordConfirmException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongPasswordConfirmException(Throwable cause) {
        super(cause);
    }

    protected WrongPasswordConfirmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
