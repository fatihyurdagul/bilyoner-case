package com.bilyoner.bettingapp.controllers;

import com.bilyoner.bettingapp.dto.request.BetSlipRequestDto;
import com.bilyoner.bettingapp.services.BetSlipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bet-slips")
@RequiredArgsConstructor
public class BetSlipController {

    private final BetSlipService betSlipService;

    @PostMapping
    public ResponseEntity<Void> createBetSlip(@Valid @RequestBody BetSlipRequestDto dto,
                                              @RequestHeader("X-Customer-Id") String customerId) {
        betSlipService.createBetSlip(dto, customerId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
