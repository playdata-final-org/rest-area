package com.restarea.mail.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {
    private String from;
    private String[] address;
    private String[] ccAddress;
    private String title;
    private String content;
}
