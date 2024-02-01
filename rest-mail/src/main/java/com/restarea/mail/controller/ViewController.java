package com.restarea.mail.controller;

import com.restarea.mail.Service.MailSendService;
import com.restarea.mail.dto.MailSendDto;
import com.restarea.mail.entity.Mail;
import com.restarea.mail.repository.MailRepository;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
@Slf4j
public class ViewController {

    @Autowired
    private final MailSendService mailSendService;
    private final MailRepository mailRepository;


    public ViewController(MailSendService mailSendService, MailRepository mailRepository) {
        this.mailSendService = mailSendService;
        this.mailRepository = mailRepository;
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
    public String outBox(Model model) {
        // 보낸 메일함의 데이터를 가져와서 모델에 추가
        List<Mail> sentMails = mailRepository.findAllByOrderBySentDateTimeDesc();
        model.addAttribute("sentMails", sentMails);
        return "email-outbox";
    }
    @GetMapping("/outbox/detail/{mailId}")
    public String outBoxDetail(@PathVariable Long mailId, Model model) {
        // mailId를 사용하여 메일 정보 조회
        Optional<Mail> mailOptional = mailRepository.findById(mailId);

        if (mailOptional.isPresent()) {
            Mail mail = mailOptional.get();
            model.addAttribute("mail", mail);
            return "email-outbox-detail";
        } else {
            // 메일이 존재하지 않을 경우 처리
            return "redirect:/outbox";
        }
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

    @GetMapping("/write")
    public String write() {
        return "email-write";
    }

    @PostMapping("/mail/send")
    public String sendMail(@RequestParam("subject") String subject,
                           @RequestParam("recipient") String[] recipient,
                           @RequestParam("cc") String[] cc,
                           @RequestParam("attachment") MultipartFile file,
                           @RequestParam("content") String content) throws MessagingException, IOException {
        MailSendDto mailDto = new MailSendDto();
        mailDto.setSubject(subject);
        mailDto.setRecipient(recipient);
        mailDto.setCc(cc);
        mailDto.setContent(content);

        mailSendService.sendMultipleMessage(mailDto, file);

        System.out.println("메일 전송 완료");
        return "email-send";
    }
    @GetMapping("/mailComplete")
    public String mailComplete(){
        return "email-send";
    }
    @GetMapping("/outbox/search")
    public String searchMail(@RequestParam("query") String query, Model model) {
        // 메일 검색 (제목 또는 수신자 또는 발신자 필드 중에서 검색)
        List<Mail> searchedMails = mailRepository.findBySubjectContainingIgnoreCaseOrRecipientContainingIgnoreCaseOrSenderContainingIgnoreCase(query, query, query);
        model.addAttribute("sentMails", searchedMails);
        return "email-outbox";
    }
    // 회신 클릭 시 기존 메일 정보와 함께 메일 작성 페이지로 이동
    @GetMapping("/reply/{mailId}")
    public String replyMail(@PathVariable Long mailId, Model model) {
        Optional<Mail> mailOptional = mailRepository.findById(mailId);
        if (mailOptional.isPresent()) {
            Mail originalMail = mailOptional.get();

            // 기존 메일의 정보를 유지하면서 회신할 메일 작성 페이지로 이동
            model.addAttribute("originalMail", originalMail);
            return "email-reply"; // 회신할 메일 작성 페이지로 이동
        } else {
            return "redirect:/outbox"; // 메일이 존재하지 않을 경우 처리
        }
    }
}
