package com.example.DevSculpt.service.alarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseEmitterService {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter createSseEmitter(Long userId) {
        SseEmitter emitter = new SseEmitter();
        emitters.put(userId, emitter);
        return emitter;
    }

    public void removeSseEmitter(Long userId) {
        emitters.remove(userId);
    }

    public void sendNotification(Long userId, String message) {
        SseEmitter emitter = emitters.get(userId);
        if (emitter != null) {
            try {
                Map<String, String> notificationData = new HashMap<>();
                notificationData.put("message", message);
                emitter.send(SseEmitter.event().data(new ObjectMapper().writeValueAsString(notificationData)));
            } catch (IOException e) {
                // Handle IO exception
            }
        }
    }
}