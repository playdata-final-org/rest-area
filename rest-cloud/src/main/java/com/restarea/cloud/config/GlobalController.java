package com.restarea.cloud.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;



@Slf4j

@ControllerAdvice
public class GlobalController {

    private String staticPath;
    public GlobalController(@Value("${resource.request-path}") String staticPath) {
        this.staticPath = staticPath;
    }





    @ModelAttribute
    public void getRequestPaths(HttpServletRequest req,

                                Model model){
        model.addAttribute("servletPath",req.getServletPath());
        model.addAttribute("staticPath",staticPath);
    }

}
