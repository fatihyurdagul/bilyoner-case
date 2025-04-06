package com.bilyoner.bettingapp.exceptions.chain.validators;

import com.bilyoner.bettingapp.dto.request.BetSlipRequestDto;
import com.bilyoner.bettingapp.entities.EventEntity;
import com.bilyoner.bettingapp.exceptions.ApiExceptionType;
import com.bilyoner.bettingapp.exceptions.BilyonerException;
import com.bilyoner.bettingapp.exceptions.chain.AbstractValidationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class RateConflictValidator extends AbstractValidationHandler {

    @Override
    protected boolean isValid(BetSlipRequestDto dto, EventEntity event) {
        if (dto.getBetRate() != null) {

            BigDecimal currentRate = event.getRateByType(dto.getBetType());

            if (dto.isRateChanged(currentRate)) {
                log.error("Rate conflict detected. Event current rate: {} User selected rate: {}", currentRate, dto.getBetRate());
                throw BilyonerException.of(ApiExceptionType.RATE_CONFLICT, currentRate, dto.getBetRate());
            }
        }
        return true;
    }
}
