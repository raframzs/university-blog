package com.raframz.universityblog.config.exception;

import com.raframz.universityblog.config.ErrorCode;

public class NotFoudResourceException extends GenericExeption {
    public NotFoudResourceException(ErrorCode errorCode) {
        super(errorCode);
    }
}
