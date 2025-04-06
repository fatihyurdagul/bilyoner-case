package com.bilyoner.bettingapp.services.impl;

import com.bilyoner.bettingapp.dto.request.BetSlipRequestDto;
import com.bilyoner.bettingapp.entities.BetSlipEntity;
import com.bilyoner.bettingapp.entities.EventEntity;
import com.bilyoner.bettingapp.exceptions.chain.ValidationChainHandler;
import com.bilyoner.bettingapp.mappers.BetSlipMapper;
import com.bilyoner.bettingapp.properties.ApplicationProperties;
import com.bilyoner.bettingapp.repositories.BetSlipRepository;
import com.bilyoner.bettingapp.services.BetSlipService;
import com.bilyoner.bettingapp.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@RequiredArgsConstructor
public class BetSlipServiceImpl implements BetSlipService {

    private final BetSlipRepository betSlipRepository;
    private final BetSlipMapper betSlipMapper;
    private final EventService eventService;

    private final TransactionTemplate transactionTemplate;
    private final ValidationChainHandler validationHandler;
    private final ApplicationProperties properties;

    @Override
    @Transactional
    public void createBetSlip(BetSlipRequestDto request, String customerId) {

        transactionTemplate.setTimeout(properties.getTransaction().getTimeout());

        transactionTemplate.execute(status -> {

            EventEntity event = eventService.getEventById(request.getEventId());

            validationHandler.validate(request, event);

            BetSlipEntity entity = betSlipMapper.toEntity(request, customerId);
            betSlipRepository.save(entity);

            eventService.increaseTotalValues(request.getEventId(), request.getQuantity(), request.getTotalAmount());

            return entity;
        });
    }
}
