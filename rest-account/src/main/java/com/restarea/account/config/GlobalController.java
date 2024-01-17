package com.restarea.account.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice("com.restarea.account.controller")
public class GlobalController {

    @ModelAttribute("servletPath")
    String getRequestServletPath(HttpServletRequest req){
        return req.getServletPath();
    }
}
