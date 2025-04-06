package com.bilyoner.bettingapp.services;

import com.bilyoner.bettingapp.dto.request.BetSlipRequestDto;

public interface BetSlipService {
    void createBetSlip(BetSlipRequestDto dto, String customerId);
}
