package com.bilyoner.bettingapp.exceptions.chain;

import com.bilyoner.bettingapp.dto.request.BetSlipRequestDto;
import com.bilyoner.bettingapp.entities.EventEntity;

public interface ValidationHandler {
    void setNextValidation(ValidationHandler next);
    void check(BetSlipRequestDto dto, EventEntity event);
}
