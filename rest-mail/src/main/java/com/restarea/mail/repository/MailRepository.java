package com.restarea.mail.repository;

import com.restarea.mail.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailRepository extends JpaRepository<Mail, Long> {

    List<Mail> findAllByOrderBySentDateTimeDesc();
    List<Mail> findBySubjectContainingIgnoreCaseOrRecipientContainingIgnoreCaseOrSenderContainingIgnoreCase(String subject, String recipient, String sender);
}
