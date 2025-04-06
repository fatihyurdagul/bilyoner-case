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
public class MaxTotalAmountValidator extends AbstractValidationHandler {

    private static final BigDecimal MAX_TOTAL_AMOUNT = new BigDecimal("10000");

    @Override
    protected boolean isValid(BetSlipRequestDto dto, EventEntity event) {

        BigDecimal betTotalAmount = dto.getTotalAmount();
        BigDecimal overallTotalAmount = event.getCurrentBetAmount().add(betTotalAmount);
        if (overallTotalAmount.compareTo(MAX_TOTAL_AMOUNT) > 0) {
            log.error("Overall total amount exceeded. Limit: {} Current: {}", MAX_TOTAL_AMOUNT, overallTotalAmount);
            throw BilyonerException.of(ApiExceptionType.EVENT_TOTAL_AMOUNT_EXCEEDED);
        }
        return true;
    }
}
