package com.restarea.mail.Service;

import com.restarea.mail.dto.MailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class  MailService {
    private final JavaMailSender emailSender;

    public void sendMultipleMessage(MailDto mailDto, MultipartFile file) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        //메일 제목 설정
        helper.setSubject(mailDto.getTitle());

        //참조자 설정
        helper.setCc(mailDto.getCcAddress());

        helper.setText(mailDto.getContent(), false);

        helper.setFrom(mailDto.getFrom());

        //첨부 파일 설정
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        helper.addAttachment(MimeUtility.encodeText(fileName, "UTF-8", "B"),
                                            new ByteArrayResource(IOUtils.toByteArray(file.getInputStream())));

        //수신자 한번에 전송
        helper.setTo(mailDto.getAddress());
        emailSender.send(message);
        log.info("mail multiple send complete.");
    }
}
