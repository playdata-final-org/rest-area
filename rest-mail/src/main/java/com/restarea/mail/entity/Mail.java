package com.restarea.mail.entity;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "mails")
@Builder
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_sequence")
    @SequenceGenerator(name = "mail_sequence", sequenceName = "MAIL_SEQUENCE", allocationSize = 1)
    private Long id;

    @NotNull
    private String subject;
    private String sender;
    private String recipient;
    private String cc;
    private String attachment;
    @Lob
    @Column(name = "attachment_data")
    private byte[] attachmentData;
    private String attachmentName;
    @Lob
    private String content;
    @Column(name = "sent_datetime")
    private Date sentDateTime;
}
