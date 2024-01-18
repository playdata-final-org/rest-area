package com.restarea.mail.controller;

import com.restarea.mail.Service.MailService;
import com.restarea.mail.dto.MailDto;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("")
@Slf4j
public class ViewController {

    private final MailService mailService;

    public ViewController(MailService mailService) {
        this.mailService = mailService;
    }

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
    @PostMapping("/mail")
    public String sendMail(MailDto mailDto, MultipartFile file) throws MessagingException, IOException {
        mailService.sendMultipleMessage(mailDto, file);
        System.out.println("메일 전송 완료");
        return "email-send";
    }
}
