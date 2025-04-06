package com.bilyoner.bettingapp.exceptions.chain;

import com.bilyoner.bettingapp.dto.request.BetSlipRequestDto;
import com.bilyoner.bettingapp.entities.EventEntity;

public abstract class AbstractValidationHandler implements ValidationHandler {

    protected ValidationHandler next;

    @Override
    public void setNextValidation(ValidationHandler next) {
        this.next = next;
    }

    @Override
    public void check(BetSlipRequestDto dto, EventEntity event) {
        if (isValid(dto, event)) {
            if (next != null) {
                next.check(dto, event);
            }
        }
    }

    protected abstract boolean isValid(BetSlipRequestDto dto, EventEntity event);
}
