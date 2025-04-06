package com.bilyoner.bettingapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDto {
    private Long id;
    private String league;
    private String homeTeam;
    private String awayTeam;
    private BigDecimal homeWinRate;
    private BigDecimal drawRate;
    private BigDecimal awayWinRate;
    private LocalDateTime startTime;
}
