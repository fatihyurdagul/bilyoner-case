package com.bilyoner.bettingapp.schedules;

import com.bilyoner.bettingapp.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class EventUpdateTask {

    private final EventService eventService;

    @Scheduled(fixedRate = 1000)
    @Transactional
    public void updateBets() {
        eventService.changeBetRatesOfEvents();
    }

}
