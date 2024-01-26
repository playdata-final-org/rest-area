package com.example.DevSculpt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dev_board")
@ToString(exclude = {"comments", "files", "user"})
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_sequence")
    @SequenceGenerator(name = "board_sequence", sequenceName = "board_sequence", allocationSize = 1)
    private Long boardId;
    private String title;

    @Lob
    private String content;
    private String category;
    private Boolean isAnswerAccepted;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime modificationDate;

    private String delYn;

    // 연관 관계 매핑 변경
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "devId")
    private UserEntity user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FileEntity> files = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> comments = new ArrayList<>(); // CommentEntity와의 양방향 관계

    public void addFile(FileEntity fileEntity) {
        files.add(fileEntity);
        fileEntity.setBoard(this);
    }

    public void removeFile(FileEntity fileEntity) {
        files.remove(fileEntity);
        fileEntity.setBoard(null);
    }

    public void addComment(CommentEntity commentEntity) {
        comments.add(commentEntity);
        commentEntity.setBoard(this);
    }

    public void removeComment(CommentEntity commentEntity) {
        comments.remove(commentEntity);
        commentEntity.setBoard(null);
    }
}