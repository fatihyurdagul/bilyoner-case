package com.bilyoner.bettingapp.exceptions.chain;

import com.bilyoner.bettingapp.dto.request.BetSlipRequestDto;
import com.bilyoner.bettingapp.entities.EventEntity;
import com.bilyoner.bettingapp.exceptions.chain.validators.MaxBetLimitValidator;
import com.bilyoner.bettingapp.exceptions.chain.validators.MaxTotalAmountValidator;
import com.bilyoner.bettingapp.exceptions.chain.validators.RateConflictValidator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidationChainHandler {

    private final RateConflictValidator oddsMismatchRule;
    private final MaxBetLimitValidator maxBetLimitValidator;
    private final MaxTotalAmountValidator maxTotalAmountValidator;

    @PostConstruct
    public void initChain() {
        oddsMismatchRule.setNextValidation(maxBetLimitValidator);
        maxBetLimitValidator.setNextValidation(maxTotalAmountValidator);
    }

    public void validate(BetSlipRequestDto dto, EventEntity event) {
        oddsMismatchRule.check(dto, event);
    }
}
