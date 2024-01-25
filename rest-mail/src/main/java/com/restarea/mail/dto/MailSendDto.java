package com.restarea.mail.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MailSendDto {
    private String subject;
    private String recipient;
    private String cc;
    private String attachment;
    private byte[] attachmentData;
    private String attachmentName;
    private String content;

}
