package com.example.DevSculpt.controller.sse;

import com.example.DevSculpt.service.alarm.SseEmitterService;
import com.example.DevSculpt.service.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sse")
public class SseController {

    private final SseEmitterService sseService;

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)    public SseEmitter subscribe() {
        // 현재 로그인된 사용자 정보 가져오기
        Long userId = getUserIdFromAuthentication();

        if (userId != null) {
            // 현재 로그인된 사용자에게 SSEEmitter 생성 및 반환
            return sseService.createSseEmitter(userId);
        } else {
            // 비로그인 사용자에게는 빈 SSEEmitter 반환
            return new SseEmitter();
        }
    }

    private Long getUserIdFromAuthentication() {
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUserDetail) {
            CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userDetails.getUserId();
        }
        return null;
    }
}