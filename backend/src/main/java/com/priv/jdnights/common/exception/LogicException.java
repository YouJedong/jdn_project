package com.priv.jdnights.common.exception;

public class LogicException extends RuntimeException {

    private final String errorCode;


    public LogicException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
