package com.example.DevSculpt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dev")
public class IndexController {
    @GetMapping("/index")
    public String index() {
        return "main/index";
    }

}
