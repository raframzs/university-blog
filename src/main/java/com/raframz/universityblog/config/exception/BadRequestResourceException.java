package com.raframz.universityblog.config.exception;

import com.raframz.universityblog.config.ErrorCode;

public class BadRequestResourceException extends GenericExeption {
    public BadRequestResourceException(ErrorCode errorCode) {
        super(errorCode);
    }
}
