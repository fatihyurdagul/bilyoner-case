package com.bilyoner.bettingapp.integration.services;

import com.bilyoner.bettingapp.dto.request.EventRequestDto;
import com.bilyoner.bettingapp.entities.EventEntity;
import com.bilyoner.bettingapp.repositories.EventRepository;
import com.bilyoner.bettingapp.services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EventServiceImplTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        eventRepository.deleteAll();
    }

    @Test
    void shouldCreateEvent() {
        EventRequestDto request = new EventRequestDto();
        request.setLeague("League");
        request.setHomeTeam("Home Team");
        request.setAwayTeam("Away Team");
        request.setHomeWinRate(BigDecimal.ONE);
        request.setAwayWinRate(BigDecimal.ONE);
        request.setDrawRate(BigDecimal.ONE);
        request.setStartTime(LocalDateTime.now().plusHours(1));

        eventService.createEvent(request);

        List<EventEntity> events = eventRepository.findAll();
        assertEquals(request.getLeague(), events.get(0).getLeague());
    }

    @Test
    void shouldIncreaseTotalValues() {
        EventRequestDto request = new EventRequestDto();
        request.setLeague("League");
        request.setHomeTeam("Home Team");
        request.setAwayTeam("Away Team");
        request.setHomeWinRate(BigDecimal.ONE);
        request.setAwayWinRate(BigDecimal.ONE);
        request.setDrawRate(BigDecimal.ONE);
        request.setStartTime(LocalDateTime.now().plusHours(1));

        eventService.createEvent(request);

        List<EventEntity> events = eventRepository.findAll();
        EventEntity event = events.get(0);
        eventService.increaseTotalValues(event.getId(), 5, BigDecimal.valueOf(100));

        EventEntity expectedEntity = eventService.getEventById(event.getId());
        assertEquals(5, expectedEntity.getCurrentBetCount());
        assertEquals(0, BigDecimal.valueOf(100).compareTo(expectedEntity.getCurrentBetAmount()));
    }
}