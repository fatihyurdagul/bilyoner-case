package com.bilyoner.bettingapp.eventEmitters;

import com.bilyoner.bettingapp.dto.response.EventResponseDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ServerSentEventManager {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter();

        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onError(e -> emitters.remove(emitter));

        emitters.add(emitter);
        return emitter;
    }

    public void sendEvent(List<EventResponseDto> events) {
        List<SseEmitter> redundantEmitters = new ArrayList<>();

        this.emitters.forEach(emitter -> {
            try {
                emitter.send(events, MediaType.APPLICATION_JSON);
            } catch (Exception ex) {
                redundantEmitters.add(emitter);
            }
        });

        this.emitters.removeAll(redundantEmitters);
    }
}

