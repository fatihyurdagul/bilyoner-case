package com.bilyoner.bettingapp.services;

import com.bilyoner.bettingapp.dto.request.EventRequestDto;
import com.bilyoner.bettingapp.entities.EventEntity;

import java.math.BigDecimal;

public interface EventService {

    void createEvent(EventRequestDto request);

    void changeBetRatesOfEvents();

    EventEntity getEventById(Long id);

    void increaseTotalValues(long eventId, int updatedBetCount, BigDecimal totalAmount);
}
