package com.example.BAS.service.mail;

import com.example.BAS.dao.user.UserDAO;
import com.example.BAS.dto.mail.MailDTO;
import com.example.BAS.entitiy.users.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service //임시비밀번호 메일 발신 서비스
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final UserDAO userDAO;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public MailDTO createMailAndChangePassword(String username) {

        String temporaryPassword = getTempPassword();

        MailDTO mailDTO = new MailDTO();
        mailDTO.setUsername(username);
        mailDTO.setTitle("BAS 임시 비밀 번호 발급 안내 메일 입니다.");
        mailDTO.setMessage("안녕하세요! BAS 임시비밀번호 발급 안내 메일 입니다."
                + " 회원님의 임시 비밀 번호는 " + temporaryPassword + " 입니다."
                + "로그인 후에 회원 정보 수정 창에서 비밀 번호를 변경을 해 주세요!");
        updatePassword(temporaryPassword,username);
        return mailDTO;
    }
    @Override
    public void updatePassword(String temporaryPassword, String username) {

        Users user = userDAO.findByUsername(username);
        if (user != null) {
            String password = bCryptPasswordEncoder.encode(temporaryPassword);
            user.setPassword(password);
            userDAO.save(user);
        }
    }
    @Override
    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String temporaryPassword = "";

        int randomPassword = 0;
        for (int i = 0; i < 10; i++) {
            randomPassword = (int) (charSet.length * Math.random());
            temporaryPassword += charSet[randomPassword];
        }
        return temporaryPassword;
    }
    @Override
    public boolean mailSend(MailDTO mailDTO) {
        try {
            log.info("성공");
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailDTO.getUsername());
            message.setSubject(mailDTO.getTitle());
            message.setText(mailDTO.getMessage());
            message.setFrom("rururia_@naver.com");
            message.setReplyTo("rururia_@naver.com");
            mailSender.send(message);

            return true;
        } catch (MailException e) {
            log.error("실패:"+e.getMessage());
            return false;
        }
    }
}