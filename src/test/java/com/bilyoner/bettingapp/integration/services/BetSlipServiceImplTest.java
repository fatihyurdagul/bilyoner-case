package com.bilyoner.bettingapp.integration.services;

import com.bilyoner.bettingapp.entities.EventEntity;
import com.bilyoner.bettingapp.repositories.EventRepository;
import com.bilyoner.bettingapp.services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@ActiveProfiles("test")
class BetSlipServiceImplTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private EventService eventService;

    private EventEntity event1;
    private EventEntity event2;

    @BeforeEach
    void setup() {
        event1 = EventEntity.builder()
                .league("TestLeague")
                .homeTeam("TestHomeTeam1")
                .awayTeam("TestAwayTeam1")
                .homeWinRate(BigDecimal.valueOf(1.85))
                .awayWinRate(BigDecimal.valueOf(3.20))
                .drawRate(BigDecimal.valueOf(2.50))
                .startTime(LocalDateTime.now().plusHours(1))
                .build();

        event2 = EventEntity.builder()
                .league("TestLeague")
                .homeTeam("TestHomeTeam2")
                .awayTeam("TestAwayTeam2")
                .homeWinRate(BigDecimal.valueOf(1.1))
                .awayWinRate(BigDecimal.valueOf(1.20))
                .drawRate(BigDecimal.valueOf(1.50))
                .startTime(LocalDateTime.now().plusHours(2))
                .build();

        eventRepository.saveAll(List.of(event1, event2));
    }

    @Test
    void shouldNotChangeBetRateWhenEventLocked() throws InterruptedException {
        // given
        EventEntity actualNotChanged = event1;
        EventEntity actualChanged = event2;

        // when
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(1);

        Runnable eventLockThread = () -> {
            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
            transactionTemplate.executeWithoutResult(status -> {

                eventService.getEventById(actualNotChanged.getId());// pessimistic lock
                latch.countDown();

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        };

        Runnable betRateUpdateThread = () -> {
            try {
                latch.await();
                eventService.changeBetRatesOfEvents();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        executor.submit(eventLockThread);
        executor.submit(betRateUpdateThread);

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        // then
        EventEntity expectedNotChangedEvent = eventRepository.findById(actualNotChanged.getId()).orElseThrow();
        EventEntity expectedChangedEvent = eventRepository.findById(actualChanged.getId()).orElseThrow();

        assertEquals(0, actualNotChanged.getHomeWinRate().compareTo(expectedNotChangedEvent.getHomeWinRate()));
        assertEquals(0, actualNotChanged.getAwayWinRate().compareTo(expectedNotChangedEvent.getAwayWinRate()));
        assertEquals(0, actualNotChanged.getDrawRate().compareTo(expectedNotChangedEvent.getDrawRate()));

        assertNotEquals(0, actualChanged.getHomeWinRate().compareTo(expectedChangedEvent.getHomeWinRate()));
        assertNotEquals(0, actualChanged.getAwayWinRate().compareTo(expectedChangedEvent.getAwayWinRate()));
        assertNotEquals(0, actualChanged.getDrawRate().compareTo(expectedChangedEvent.getDrawRate()));
    }

    @Test
    void shouldChangeBetRateWhenEventNotLocked() {

        // given
        EventEntity actualChanged1 = event1;
        EventEntity actualChanged2 = event2;

        // when
        eventService.changeBetRatesOfEvents();

        // then
        EventEntity expectedChangedEvent1 = eventRepository.findById(actualChanged1.getId()).orElseThrow();
        EventEntity expectedChangedEvent2 = eventRepository.findById(actualChanged2.getId()).orElseThrow();

        assertNotEquals(0, actualChanged1.getHomeWinRate().compareTo(expectedChangedEvent1.getHomeWinRate()));
        assertNotEquals(0, actualChanged1.getAwayWinRate().compareTo(expectedChangedEvent1.getAwayWinRate()));
        assertNotEquals(0, actualChanged1.getDrawRate().compareTo(expectedChangedEvent1.getDrawRate()));

        assertNotEquals(0, actualChanged2.getHomeWinRate().compareTo(expectedChangedEvent2.getHomeWinRate()));
        assertNotEquals(0, actualChanged2.getAwayWinRate().compareTo(expectedChangedEvent2.getAwayWinRate()));
        assertNotEquals(0, actualChanged2.getDrawRate().compareTo(expectedChangedEvent2.getDrawRate()));
    }


}