package com.raframz.universityblog.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.raframz.universityblog.config.exception.BadRequestResourceException;
import com.raframz.universityblog.config.exception.NotFoudResourceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    private final HttpServletRequest request;

    public ErrorHandler(HttpServletRequest request) {
        this.request = request;
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handle(Throwable ex) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.INTERNAL_SERVER_ERROR, ex, ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoudResourceException.class)
    public ResponseEntity<ErrorResponse> handle(NotFoudResourceException ex) {
        log.error(HttpStatus.NOT_FOUND.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.NOT_FOUND, ex, ex.getErrorCode());
    }

    @ExceptionHandler(BadRequestResourceException.class)
    public ResponseEntity<ErrorResponse> handle(BadRequestResourceException ex) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.BAD_REQUEST, ex, ex.getErrorCode());
    }

    private ResponseEntity<ErrorResponse> buildResponseError(HttpStatus httpStatus,
                                                             Throwable ex, ErrorCode errorCode) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .detail(errorCode.getDescription())
                .name(httpStatus.getReasonPhrase())
                .status(httpStatus.value())
                .code(errorCode.getCode())
                .id("")
                .resource(request.getRequestURI())
                .build();
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @Builder
    @NonNull
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    static class ErrorResponse {
        private static final String DATE_PATTERN = "yyy-mm-dd'T'HH:mm:ss[.SSSSSSSSS]['Z']";
        @JsonProperty
        private String name;
        @JsonProperty
        private Integer status;
        @JsonProperty
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
        private LocalDateTime timestamp;
        @JsonProperty
        private Integer code;
        @JsonProperty
        private String resource;
        @JsonProperty
        private String id;
        @JsonProperty
        private String detail;

    }

}
