package com.bilyoner.bettingapp.exceptions;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.text.MessageFormat;
import java.util.Objects;

@Getter
public enum ApiExceptionType {
    FIELD_VALIDATION_ERROR(1, "Field validation exception", HttpStatus.BAD_REQUEST),
    FIELD_MISSING_ERROR(2, "Missing field", HttpStatus.BAD_REQUEST),
    NOT_FOUND_ENTITY(3, "Entity not found", HttpStatus.BAD_REQUEST),
    RATE_CONFLICT(4, "Rate conflict. Current Rate {0}, Selected Rate: {1}", HttpStatus.BAD_REQUEST),
    EVENT_TOTAL_AMOUNT_EXCEEDED(5, "Event total amount exceeded.", HttpStatus.BAD_REQUEST),
    EVENT_BET_LIMIT_EXCEEDED(6, "Event bet limit exceeded.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_CUSTOMER(7, "unauthorized customer", HttpStatus.UNAUTHORIZED);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
    @Setter
    private String[] parameters;

    ApiExceptionType(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        if (Objects.nonNull(parameters) && parameters.length > 0) {
            return MessageFormat.format(this.message, parameters);
        }
        return message;
    }
}