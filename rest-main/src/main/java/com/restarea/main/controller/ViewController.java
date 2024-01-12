package com.restarea.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
@CrossOrigin
public class ViewController {
    @GetMapping("")
    public String indexView(){
        return "index";
    }

    @GetMapping("/intro/mail")
    public String IntroMailView() { return "/intro/mail"; }

    @GetMapping("/intro/cloud")
    public String IntroCloudView() { return "intro/cloud";}
}
