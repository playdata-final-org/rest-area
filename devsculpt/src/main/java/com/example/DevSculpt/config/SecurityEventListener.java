package com.example.DevSculpt.config;

import com.example.DevSculpt.service.alarm.SseEmitterService;
import com.example.DevSculpt.service.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityEventListener {
    private final SseEmitterService sseEmitterService;

    @EventListener
    public void handleAuthenticationSuccess(AuthenticationSuccessEvent event) {
        // 로그인 성공할시 SSE 구독시작
        Long userId = getUserIdFromAuthentication(event.getAuthentication());
        if (userId != null) {
            sseEmitterService.createSseEmitter(userId);
        }
    }

    @EventListener
    public void handleLogoutSuccess(LogoutSuccessEvent event) {
        // 로그아웃시 SSE 구독취소
        Long userId = getUserIdFromAuthentication(event.getAuthentication());
        if (userId != null) {
            sseEmitterService.removeSseEmitter(userId);
        }
    }

    @EventListener
    public Long getUserIdFromAuthentication(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetail) {
            CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
            return userDetails.getUserId();
        }
        return null;
    }
}