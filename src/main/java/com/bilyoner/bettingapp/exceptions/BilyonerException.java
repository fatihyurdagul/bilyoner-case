package com.bilyoner.bettingapp.exceptions;

import java.util.Arrays;

public class BilyonerException extends BaseRuntimeException {

    public static BilyonerException of(ApiExceptionType exceptionType) {
        return new BilyonerException(exceptionType);
    }

    public static BilyonerException of(ApiExceptionType exceptionType, Object... parameters) {
        String[] newParameters = Arrays.stream(parameters).map(String::valueOf).toArray(String[]::new);
        exceptionType.setParameters(newParameters);
        return new BilyonerException(exceptionType);
    }

    public BilyonerException(ApiExceptionType exceptionType) {
        super(exceptionType);
    }

}
