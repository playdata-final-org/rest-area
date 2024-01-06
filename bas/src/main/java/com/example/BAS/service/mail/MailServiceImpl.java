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

    @Override // 발신 메일 정보를 생성하고 임시 비밀번호로 회원 비밀번호를 변경
    public MailDTO createMailAndChangePassword(String email) {
        //임시 비밀번호 생성
        String temporaryPassword = getTempPassword();

        //MailDTO 객체 생성해서 이메일 발신 정보를 저장
        MailDTO mailDTO = new MailDTO();
        mailDTO.setEmail(email);
        mailDTO.setTitle("BAS 임시 비밀 번호 발급 안내 메일 입니다.");
        mailDTO.setMessage("안녕하세요! BAS 임시비밀번호 발급 안내 메일 입니다."
                + " 회원님의 임시 비밀 번호는 " + temporaryPassword + " 입니다."
                + "로그인 후에 회원 정보 수정 창에서 비밀 번호를 변경을 해 주세요!");
        // TODO-1 잘못된 위치, mail 에서 password 를 변경하고 있음
        // TODO-1 비밀번호를 변경한 후 메일을 보내고 있음, 만약 메일 전송이 실패했다면?
        // 사용자의 비빌번호를 암호화 된 임시 비밀번호로 업데이트
        updatePassword(temporaryPassword,email);
        return mailDTO;
    }


    @Override //임시 비밀번호를 암호화 해서 DB에 저장
    public void updatePassword(String temporaryPassword, String email) {
        // 사용자가 적은 email DB에서 유무 확인
        Users user = userDAO.findByEmail(email);
        // 사용자가 존재 하면 temporaryPassword(임시비밀번호)를 암호화해서 DB에 업데이트
        if (user != null) {
            String password = bCryptPasswordEncoder.encode(temporaryPassword);
            user.setPassword(password);
            userDAO.save(user);
        }
    }

    @Override //임시비밀번호 생성
    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String temporaryPassword = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성
        int randomPassword = 0;
        for (int i = 0; i < 10; i++) {
            //charSet에서 문자를 선택하여 랜덤한 문자열 생성
            randomPassword = (int) (charSet.length * Math.random());
            temporaryPassword += charSet[randomPassword];
        }
        return temporaryPassword;
    }

    @Override // 이메일 발신
    public boolean mailSend(MailDTO mailDTO) {
        try {
            log.info("성공");
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailDTO.getEmail());
            message.setSubject(mailDTO.getTitle());
            message.setText(mailDTO.getMessage());
            message.setFrom("rururia_@naver.com");
            message.setReplyTo("rururia_@naver.com");
            mailSender.send(message);
            log.info("message=>[]"+message);

            return true; // 메일 발송 성공
        } catch (MailException e) {
            log.error("실패:"+e.getMessage());
            return false; // 메일 발송 실패
        }
    }






}