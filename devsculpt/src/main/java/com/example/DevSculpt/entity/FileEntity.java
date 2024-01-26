package com.example.DevSculpt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dev_files")
public class FileEntity {
    @Id
    @GeneratedValue
    private Long fileId;

    private String origFilename;
    private String fileName;
    private String filePath;

    @CreationTimestamp
    private LocalDateTime registerDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity board;
    private Long devId;

    public void setBoard(BoardEntity board) {
        this.board = board;
        if (board != null) {
            board.getFiles().add(this);
        }
    }
}