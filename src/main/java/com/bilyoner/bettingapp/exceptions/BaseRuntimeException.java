package com.bilyoner.bettingapp.exceptions;

import lombok.Getter;

@Getter
public abstract class BaseRuntimeException extends RuntimeException {

    private final ApiExceptionType exceptionType;

    protected BaseRuntimeException(ApiExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

}