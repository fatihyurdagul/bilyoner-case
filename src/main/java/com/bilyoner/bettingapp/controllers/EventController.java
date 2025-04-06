package com.bilyoner.bettingapp.controllers;

import com.bilyoner.bettingapp.dto.request.EventRequestDto;
import com.bilyoner.bettingapp.eventEmitters.ServerSentEventManager;
import com.bilyoner.bettingapp.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final ServerSentEventManager emitterManager;

    @PostMapping
    public ResponseEntity<Void> createEvent(@Valid @RequestBody EventRequestDto dto) {
        eventService.createEvent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter getAllEvents() {
        return emitterManager.subscribe();
    }
}
