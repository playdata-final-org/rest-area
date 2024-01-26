package com.example.BAS.controller.mail;

import com.example.BAS.dao.user.UserDAO;
import com.example.BAS.dto.mail.MailDTO;
import com.example.BAS.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MailController {
    private final MailService mailService;
    private final UserDAO userDAO;

    @GetMapping("/password-search") //비밀번호 찾기 페이지
    public String passwordSearch() {
        return "user/password-search";
    }

    @Transactional
    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam("username") String username, Model model) {

        if (!userDAO.existsByUsername(username)) {
            model.addAttribute("errorMessage", "존재하지 않는 이메일입니다.");
            return "user/password-search";
        }

        MailDTO mailDTO = mailService.createMailAndChangePassword(username);

        boolean mailSend = mailService.mailSend(mailDTO);
        if (mailSend) {
            model.addAttribute("successMessage", "이메일 전송 성공 ");
            return "successPage/successPage";
        } else {
            model.addAttribute("errorMessage", "이메일 전송 실패");
            return "user/password-search";
        }
    }
}

