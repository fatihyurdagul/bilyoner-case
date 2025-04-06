package com.bilyoner.bettingapp.dto.response;

import com.bilyoner.bettingapp.exceptions.ApiExceptionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ExceptionResponseDto {
    private String message;
    private ApiExceptionType type;
    private int code;
    private List<MissingFieldData> errors;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MissingFieldData {
        private String fieldName;
        private String message;
    }

    public static ExceptionResponseDto buildExceptionResponse(ApiExceptionType exception,
                                                              List<MissingFieldData> errors) {
        return ExceptionResponseDto.builder()
                .type(exception)
                .code(exception.getCode())
                .message(exception.getMessage())
                .errors(errors)
                .build();
    }
}
