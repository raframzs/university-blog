package com.raframz.universityblog.config;

public enum ErrorCode {
    INTERNAL_SERVER_ERROR(100, "En la petición algunos campos no son correctos o faltan."),
    BAD_REQUEST(101, "En la petición algunos campos no son correctos o faltan."),
    NOT_FOUND(102, "No se encontro el recurso."),
    ;
    private final Integer code;
    private final String description;

    ErrorCode(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
