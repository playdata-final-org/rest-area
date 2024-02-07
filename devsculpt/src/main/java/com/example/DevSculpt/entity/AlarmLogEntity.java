package com.example.DevSculpt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dev_alarm")
public class AlarmLogEntity {
    @Id
    @GeneratedValue
    private Long alarmId;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "commentId") // CommentEntity 테이블의 commentId를 참조
    private CommentEntity comment;

    @CreationTimestamp
    private LocalDateTime creationDate;
}