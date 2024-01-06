package com.example.BAS.controller.mail;

import com.example.BAS.dao.user.UserDAO;
import com.example.BAS.dto.mail.MailDTO;
import com.example.BAS.service.mail.MailService;
import com.example.BAS.service.users.UsersService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // TODO-1 비밀번호 찾기는 없습니다, 비밀번호 재설정만이 필요함
    @GetMapping("/password-search") //비밀번호 찾기 페이지
    public String passwordSearch() {
        return "user/password-search";
    }

    @Transactional
    @PostMapping("/sendEmail") // TODO-1 rest-api rule 어김, 어떠한 목적인지 파악하기 힘든 api 및 method 명
    public String sendEmail(@RequestParam("email") String email, Model model) {
        // 먼저 회원이 입력한 이메일이 디비에 존재하는지 확인
        if (!userDAO.existsByEmail(email)) { // TODO-1 GOOD
            model.addAttribute("errorMessage", "존재하지 않는 이메일입니다.");
            return "user/password-search";
        }

        // 있으면 다음 단계: 임시 비밀번호를 담은 메일을 생성
        MailDTO mailDTO = mailService.createMailAndChangePassword(email);
        log.info("MailController mailDTO=>[]" + mailDTO);

        // 성공 여부
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

