package com.bilyoner.bettingapp.exceptions;

import com.bilyoner.bettingapp.dto.response.ExceptionResponseDto;
import com.bilyoner.bettingapp.dto.response.ExceptionResponseDto.MissingFieldData;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDto> handleValidationError(MethodArgumentNotValidException ex) {

        List<MissingFieldData> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof FieldError err) {
                errors.add(new MissingFieldData(err.getField(), err.getDefaultMessage()));
            } else {
                errors.add(new MissingFieldData(error.getObjectName(), error.getDefaultMessage()));
            }
        });

        return createErrorResponse(ApiExceptionType.FIELD_VALIDATION_ERROR, errors);
    }

    @ExceptionHandler(BilyonerException.class)
    public ResponseEntity<ExceptionResponseDto> handleServiceExceptions(BilyonerException exception) {
        return createErrorResponse(exception.getExceptionType());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionResponseDto> handleMissingMethodArgument(MissingServletRequestParameterException e) {
        MissingFieldData fieldError = MissingFieldData.builder()
                .fieldName(e.getParameterName())
                .message(e.getLocalizedMessage())
                .build();
        return createErrorResponse(ApiExceptionType.FIELD_MISSING_ERROR, Collections.singletonList(fieldError));
    }

    private ResponseEntity<ExceptionResponseDto> createErrorResponse(ApiExceptionType exceptionType,
                                                                     List<MissingFieldData> fieldErrors) {
        ExceptionResponseDto response = ExceptionResponseDto.buildExceptionResponse(exceptionType, fieldErrors);
        return new ResponseEntity<>(response, exceptionType.getHttpStatus());
    }

    private ResponseEntity<ExceptionResponseDto> createErrorResponse(ApiExceptionType exceptionType) {
        ExceptionResponseDto response = ExceptionResponseDto.buildExceptionResponse(exceptionType, Collections.emptyList());
        return new ResponseEntity<>(response, exceptionType.getHttpStatus());
    }

}
