package com.example.DevSculpt.controller.sse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SseViewController {

    @GetMapping("/dev/noti")
    public String showNotificationPage() {
        return "notification";
    }
}