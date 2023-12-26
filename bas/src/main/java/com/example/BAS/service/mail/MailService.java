package com.example.BAS.service.mail;

import com.example.BAS.dto.mail.MailDTO;

public interface MailService {

    MailDTO createMailAndChangePassword(String email);
    void updatePassword(String temporaryPassword, String email);
    String getTempPassword();
    boolean mailSend(MailDTO mailDto);

}
