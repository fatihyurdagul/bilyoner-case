package com.bilyoner.bettingapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EventRequestDto {

    @NotBlank
    private String league;

    @NotBlank
    private String homeTeam;

    @NotBlank
    private String awayTeam;

    @NotNull
    private BigDecimal homeWinRate;

    @NotNull
    private BigDecimal drawRate;

    @NotNull
    private BigDecimal awayWinRate;

    @NotNull
    private LocalDateTime startTime;
}
