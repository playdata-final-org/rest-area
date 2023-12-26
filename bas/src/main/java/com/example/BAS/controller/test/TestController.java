package com.example.BAS.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bAs")
public class TestController {
    @GetMapping("/test")
    public String test(){
        return "test/test";
    }

}
