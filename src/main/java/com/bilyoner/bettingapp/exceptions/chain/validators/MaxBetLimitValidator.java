package com.bilyoner.bettingapp.exceptions.chain.validators;

import com.bilyoner.bettingapp.dto.request.BetSlipRequestDto;
import com.bilyoner.bettingapp.entities.EventEntity;
import com.bilyoner.bettingapp.exceptions.ApiExceptionType;
import com.bilyoner.bettingapp.exceptions.BilyonerException;
import com.bilyoner.bettingapp.exceptions.chain.AbstractValidationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MaxBetLimitValidator extends AbstractValidationHandler {

    private static final int MAX_BET_LIMIT = 500;

    @Override
    protected boolean isValid(BetSlipRequestDto dto, EventEntity event) {
        int total = event.getCurrentBetCount() + dto.getQuantity();
        if (total > MAX_BET_LIMIT) {
            log.error("Event bet limit exceeded. Limit: {}, Current: {}", MAX_BET_LIMIT, total);
            throw BilyonerException.of(ApiExceptionType.EVENT_BET_LIMIT_EXCEEDED);
        }
        return true;
    }
}
