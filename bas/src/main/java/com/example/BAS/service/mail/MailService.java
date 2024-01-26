package com.example.BAS.service.mail;

import com.example.BAS.dto.mail.MailDTO;

public interface MailService {

    MailDTO createMailAndChangePassword(String username);
    void updatePassword(String temporaryPassword, String username);
    String getTempPassword();
    boolean mailSend(MailDTO mailDto);

}
