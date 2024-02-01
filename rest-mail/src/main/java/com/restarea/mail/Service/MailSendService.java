package com.restarea.mail.Service;

import com.restarea.mail.dto.MailSendDto;
import com.restarea.mail.entity.Mail;
import com.restarea.mail.repository.MailRepository;
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
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailSendService {

    private final MailRepository mailRepository;
    private final JavaMailSender emailSender;

    public void sendMultipleMessage(MailSendDto mailDto, MultipartFile file) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // 메일 제목 설정
        helper.setSubject(mailDto.getSubject());

        // 참조자 설정 (CC)
        if (!StringUtils.isEmpty(mailDto.getCc())) {
            helper.setCc(mailDto.getCc());
        }

        helper.setText(mailDto.getContent(), true);

        //첨부 파일 설정
        if (file != null && !file.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            byte[] attachmentData = IOUtils.toByteArray(file.getInputStream());
            helper.addAttachment(MimeUtility.encodeText(fileName, "UTF-8", "B"),
                    new ByteArrayResource(attachmentData));

            mailDto.setAttachmentName(fileName);
        }

        // 저장할 메일 객체 생성 및 저장
        Mail newMail = Mail.builder()
                .subject(mailDto.getSubject())
                .sender("test@gmail.com")
                .recipient(String.join(", " , mailDto.getRecipient()))
                .cc(String.join(", " , mailDto.getCc()))
                .attachment(mailDto.getAttachment())
                .attachmentData(file != null ? file.getBytes() : null)
                .attachmentName(mailDto.getAttachmentName())
                .content(mailDto.getContent())
                .sentDateTime(new Date())
                .build();

        // 수신자 한번에 전송
        helper.setTo(mailDto.getRecipient());
        emailSender.send(message);
        log.info("Mail send complete.");

        // 메일 정보 저장
        mailRepository.save(newMail);
    }


}
