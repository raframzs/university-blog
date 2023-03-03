package com.raframz.universityblog.config.exception;


import com.raframz.universityblog.config.ErrorCode;
import org.apache.commons.lang3.StringUtils;

public abstract class GenericExeption extends RuntimeException {

    private static final String SPACE = StringUtils.SPACE;

    private static final String COMMA = ",";

    private final ErrorCode errorCode;

    protected GenericExeption(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    protected GenericExeption(ErrorCode errorCode, String message) {
        super(buildMessage(message, errorCode));
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    private static String buildMessage(String message, ErrorCode errorCode) {
        return errorCode.getDescription() + COMMA + SPACE + message;
    }

}
