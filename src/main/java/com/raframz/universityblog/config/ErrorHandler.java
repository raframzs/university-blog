package com.raframz.universityblog.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.raframz.universityblog.config.exception.BadRequestResourceException;
import com.raframz.universityblog.config.exception.NotFoudResourceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    private final HttpServletRequest request;

    private static final String UNKNOWN_FIELD = "Campo Desconocido";

    public ErrorHandler(HttpServletRequest request) {
        this.request = request;
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handle(Throwable ex) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoudResourceException.class)
    public ResponseEntity<ErrorResponse> handle(NotFoudResourceException ex) {
        log.error(HttpStatus.NOT_FOUND.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.NOT_FOUND, ex.getErrorCode());
    }

    @ExceptionHandler(BadRequestResourceException.class)
    public ResponseEntity<ErrorResponse> handle(BadRequestResourceException ex) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.BAD_REQUEST, ex.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException ex) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
        return buildResponseError(
                getFieldErrorMessage(ex));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handle(HttpMessageNotReadableException ex) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
        String detail;
        if (ex.getCause() instanceof JsonMappingException)
            detail = getFieldErrorMessage((BindException) ex.getCause());
        else
            detail = ex.getLocalizedMessage();
        return buildResponseError(
                detail);
    }

    private ResponseEntity<ErrorResponse> buildResponseError(HttpStatus httpStatus,
                                                             ErrorCode errorCode) {

        ErrorResponse errorResponse = getErrorResponse(httpStatus, errorCode);
        errorResponse.setDetail(errorCode.getDescription());
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    private ResponseEntity<ErrorResponse> buildResponseError(String detail) {

        ErrorResponse errorResponse = getErrorResponse(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST);
        errorResponse.setDetail(detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse getErrorResponse(HttpStatus httpStatus,
                                           ErrorCode errorCode) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .name(httpStatus.getReasonPhrase())
                .status(httpStatus.value())
                .code(errorCode.getCode())
                .id("")
                .resource(request.getRequestURI())
                .build();
    }

    private String getFieldErrorMessage(BindException ex) {
        final FieldError fieldError = ex.getFieldError();
        String jsonPropertyString;
        try {
            String fieldName = Objects.requireNonNull(fieldError).getField();
            Field field = Objects.requireNonNull(ex.getBindingResult().getTarget())
                    .getClass().getDeclaredField(fieldName);
            JsonProperty annotation = field.getAnnotation(JsonProperty.class);
            jsonPropertyString = annotation.value();
        } catch (Exception e) {
            jsonPropertyString = Optional.ofNullable(fieldError)
                    .map(FieldError::getField)
                    .orElse(UNKNOWN_FIELD);
        }

        final String defaultMessage = Optional.ofNullable(fieldError)
                .map(FieldError::getField)
                .orElse(UNKNOWN_FIELD);

        return String.format("%s: %s", jsonPropertyString, defaultMessage);
    }

    @Data
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
