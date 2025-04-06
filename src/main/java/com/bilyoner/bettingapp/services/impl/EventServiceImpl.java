package com.bilyoner.bettingapp.services.impl;

import com.bilyoner.bettingapp.dto.request.EventRequestDto;
import com.bilyoner.bettingapp.dto.response.EventResponseDto;
import com.bilyoner.bettingapp.entities.EventEntity;
import com.bilyoner.bettingapp.eventEmitters.ServerSentEventManager;
import com.bilyoner.bettingapp.exceptions.ApiExceptionType;
import com.bilyoner.bettingapp.exceptions.BilyonerException;
import com.bilyoner.bettingapp.mappers.EventMapper;
import com.bilyoner.bettingapp.repositories.EventRepository;
import com.bilyoner.bettingapp.services.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final ServerSentEventManager emitterManager;
    private final Random random = new Random();

    @Override
    @Transactional
    public void createEvent(EventRequestDto request) {
        EventEntity event = eventMapper.toEntity(request);
        eventRepository.save(event);
    }

    @Override
    public void changeBetRatesOfEvents() {
        List<EventEntity> events = getAllEvents();

        List<EventEntity> updatedEvents = new ArrayList<>();
        for (EventEntity event : events) {
            try {
                event.changeEventRates(random);
                eventRepository.save(event);
                updatedEvents.add(event);
            } catch (Exception e) {
                log.warn("Event {} lock altında. Güncelleme atlandı.", event.getId());
                log.warn("Oran güncellenemedi. Event işlemde: {}", event.getId());
                log.warn("pesimistic lock Oran güncellenemedi. Event işlemde: {}", event.getId());
            }
        }

        sendUpdatedEvents(updatedEvents);
    }

    private List<EventEntity> getAllEvents() {
        return eventRepository.findAll();
    }

    private void sendUpdatedEvents(List<EventEntity> events) {
        List<EventResponseDto> updatedEvents = events.stream().map(eventMapper::toResponseDto).toList();
        emitterManager.sendEvent(updatedEvents);
    }

    @Override
    @Transactional(readOnly = true)
    public EventEntity getEventById(Long id) {
        return eventRepository.findEventById(id).orElseThrow(() -> BilyonerException.of(ApiExceptionType.NOT_FOUND_ENTITY));
    }

    @Override
    @Transactional
    public void increaseTotalValues(long eventId, int betQuantity, BigDecimal betAmount) {
        EventEntity event = getEventById(eventId);

        event.updateTotalValues(betQuantity, betAmount);
        eventRepository.save(event);
    }
}
