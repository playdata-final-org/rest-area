package com.example.DevSculpt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dev_comment")
public class CommentEntity {
    @Id
    @GeneratedValue
    private Long commentId;
    private String content;
    private String nickName;
    private String profileImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private BoardEntity board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "devId")
    private UserEntity user;

    @OneToOne(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private AlarmLogEntity alarm;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime modificationDate;

    public CommentEntity(BoardEntity board, String content, String nickName, String profileImage, UserEntity user) {
        this.board = board;
        this.content = content;
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.user = user;
        this.alarm = new AlarmLogEntity();
        this.alarm.setMessage(nickName + " 님께서 댓글을 남기셨습니다.");
        this.alarm.setUser(board.getUser());
    }
}