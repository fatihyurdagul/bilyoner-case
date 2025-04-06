package com.bilyoner.bettingapp.dto.request;

import com.bilyoner.bettingapp.enums.BetType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

@Data
public class BetSlipRequestDto {

    @NotNull
    private Long eventId;

    @NotNull
    private BetType betType;

    @NotNull
    private BigDecimal betAmount;

    @NotNull
    @Min(1)
    private Integer quantity;

    private BigDecimal betRate;

    public boolean isRateChanged(BigDecimal selectedRate) {
        if (Objects.isNull(betRate)) {
            return false;
        }
        return selectedRate.compareTo(this.betRate) != 0;
    }

    public BigDecimal getTotalAmount() {
        return BigDecimal.valueOf(this.quantity).multiply(this.betAmount);
    }
}
