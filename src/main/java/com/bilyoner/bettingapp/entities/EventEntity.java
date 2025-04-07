package com.bilyoner.bettingapp.entities;

import com.bilyoner.bettingapp.enums.BetType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@Entity
@Table(name = "events")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String league;

    @Column(name = "home_team", nullable = false)
    private String homeTeam;

    @Column(name = "away_team", nullable = false)
    private String awayTeam;

    @Column(name = "home_win_rate", nullable = false)
    private BigDecimal homeWinRate;

    @Column(name = "draw_rate", nullable = false)
    private BigDecimal drawRate;

    @Column(name = "away_win_rate", nullable = false)
    private BigDecimal awayWinRate;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "current_bet_count")
    private int currentBetCount;

    @Column(name = "current_bet_amount")
    private BigDecimal currentBetAmount;

    public BigDecimal getCurrentBetAmount() {
        if (Objects.isNull(currentBetAmount)) {
            currentBetAmount = BigDecimal.ZERO;
        }
        return currentBetAmount;
    }

    public void changeEventRates(Random random) {
        this.homeWinRate = changeRate(random, homeWinRate);
        this.drawRate = changeRate(random, drawRate);
        this.awayWinRate = changeRate(random, awayWinRate);
    }

    private BigDecimal changeRate(Random random, BigDecimal currentRate) {
        double interval = 0.4;
        double result = (random.nextDouble() * 2 * interval) - interval;
        return currentRate.add(BigDecimal.valueOf(result)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getRateByType(BetType betType) {
        return switch (betType) {
            case HOME_WIN -> this.homeWinRate;
            case DRAW -> this.drawRate;
            case AWAY_WIN -> this.awayWinRate;
        };
    }

    public void updateTotalValues(int betQuantity, BigDecimal totalAmount) {
        this.currentBetCount += betQuantity;
        this.currentBetAmount = getCurrentBetAmount().add(totalAmount);
    }
}
