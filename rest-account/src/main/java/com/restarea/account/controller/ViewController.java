package com.restarea.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
@CrossOrigin
public class ViewController {
    @GetMapping("")
    public String mainView(){
        return "redirect:/login";
    }
    @GetMapping("{param}")
    public String indexView(@PathVariable("param") String param){
        return "/"+param;
    }
    @GetMapping("/policy/{page}")
    public String policyView(@PathVariable("page") String page){
        return "/policy/"+page;
    }
    @GetMapping("/forget/{page}")
    public String forgetView(@PathVariable("page") String page){
        return "/forget/"+page;
    }
    @GetMapping("/manage/{page}")
    public String manageView(@PathVariable("page") String page){
        return "/manage/"+page;
    }
    @GetMapping("/login")
    public String loginView(){
        return "/login";
    }
}
