package com.example.DevSculpt.service.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        request.getSession().setAttribute("user", customUserDetail.getUserResponseDTO());
        request.getSession().setAttribute("devId", customUserDetail.getUserResponseDTO().getDevId());
        response.sendRedirect("/dev/index");
    }
}