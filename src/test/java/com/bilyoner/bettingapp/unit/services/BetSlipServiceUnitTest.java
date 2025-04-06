package com.bilyoner.bettingapp.unit.services;

import com.bilyoner.bettingapp.dto.request.BetSlipRequestDto;
import com.bilyoner.bettingapp.entities.BetSlipEntity;
import com.bilyoner.bettingapp.entities.EventEntity;
import com.bilyoner.bettingapp.enums.BetType;
import com.bilyoner.bettingapp.mappers.BetSlipMapper;
import com.bilyoner.bettingapp.properties.ApplicationProperties;
import com.bilyoner.bettingapp.properties.TransactionProperties;
import com.bilyoner.bettingapp.services.impl.BetSlipServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BetSlipServiceUnitTest {

    @InjectMocks
    private BetSlipServiceImpl bettingService;
    @Mock
    public ApplicationProperties applicationProperties;
    @Mock
    public TransactionProperties transactionProperties;
    @Mock
    public TransactionTemplate transactionTemplate;

    private EventEntity event;

    private BetSlipMapper betSlipMapper;

    @BeforeEach
    void setup() {
        event = EventEntity.builder()
                .id(1L)
                .league("TestLeague")
                .homeTeam("TestHomeTeam1")
                .awayTeam("TestAwayTeam1")
                .homeWinRate(BigDecimal.valueOf(1.85))
                .awayWinRate(BigDecimal.valueOf(3.20))
                .drawRate(BigDecimal.valueOf(2.50))
                .startTime(LocalDateTime.now().plusHours(1))
                .build();

        betSlipMapper = Mappers.getMapper(BetSlipMapper.class);
    }

    @Test
    void shouldCorrectEntityMappingWhenCreateBetSlip() {
        // given
        BetSlipRequestDto actualRequest = new BetSlipRequestDto();
        actualRequest.setEventId(event.getId());
        actualRequest.setBetRate(BigDecimal.valueOf(1.1));
        actualRequest.setBetAmount(BigDecimal.valueOf(100));
        actualRequest.setQuantity(2);
        actualRequest.setBetType(BetType.AWAY_WIN);

        String customerId = "fatih";

        // when
        when(applicationProperties.getTransaction()).thenReturn(transactionProperties);
        when(transactionProperties.getTimeout()).thenReturn(2);
        BetSlipEntity expectedEntity = betSlipMapper.toEntity(actualRequest, customerId);

        // then
        bettingService.createBetSlip(actualRequest, customerId);

        assertEquals(customerId, expectedEntity.getCustomerId());
        assertEquals(BigDecimal.valueOf(200), expectedEntity.getTotalAmount());
        assertEquals(event.getId(), expectedEntity.getEvent().getId());
    }

}