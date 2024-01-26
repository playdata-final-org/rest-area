package com.example.BAS.config.security;


import com.example.BAS.dao.user.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class CustomSuccessHandlerBean extends SimpleUrlAuthenticationSuccessHandler {
    private final UserDAO userDAO;

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, IOException {
        String targetUrl = roleTargetUrl(authentication);

        if (response.isCommitted()) {
            return;
        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String roleTargetUrl(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        if (authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_BOOSTER"))) {
            return "/creator-search";
        } else if (authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_CREATOR"))) {
            Long userId = principalDetails.getUsers().getUserId();
            Long blogId = userDAO.getBlogIdByUserId(userId);

            return "/blog/" + blogId;
        } else {
            return principalDetails.rolePage();
        }
    }
}
