package com.restarea.mail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class ViewController {
    @GetMapping("/inbox")
    public String inBox() {
        return "email-inbox";
    }
    @GetMapping("/inbox/detail")
    public String inBoxDetail() {
        return "email-inbox-detail";
    }
    @GetMapping("/outbox")
    public String outBox() {
        return "email-outbox";
    }
    @GetMapping("/outbox/detail")
    public String outBoxDetail() {
        return "email-outbox-detail";
    }
    @GetMapping("/favorites")
    public String favorites() {
        return "email-favorites";
    }
    @GetMapping("/favorites/detail")
    public String favoritesDetail() {
        return "email-favorites-detail";
    }
    @GetMapping("/trash")
    public String trash() {
        return "email-trash";
    }
    @GetMapping("/trash/detail")
    public String trashDetail() {
        return "email-trash-detail";
    }
    @GetMapping("/write")
    public String write() {
        return "email-write";
    }
}
