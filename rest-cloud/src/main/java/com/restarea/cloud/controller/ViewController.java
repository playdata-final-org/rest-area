package com.restarea.cloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
@CrossOrigin
public class ViewController {
//    @GetMapping("")
//    public String mainView(){
//        return "redirect:/login";
//    }
    @GetMapping("")
    public String indexView(){
        return "index";
    }
    @GetMapping("{param}")
    public String indexView(@PathVariable("param") String param){
        return "/"+param;
    }
    @GetMapping("/agree/{page}")
    public String agreeView(@PathVariable("page") String page){
        return "/agree/"+page;
    }
    @GetMapping("/forget/{page}")
    public String forgetView(@PathVariable("page") String page){
        return "/forget/"+page;
    }
    @GetMapping("/service/oauth")
    public String manageView(){
        return "/service/oauth";
    }
    @GetMapping("/login")
    public String loginView(){
        return "/login";
    }



}
