package com.example.BAS.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MailDTO {
    private String username;
    private String title;
    private String message;
}
