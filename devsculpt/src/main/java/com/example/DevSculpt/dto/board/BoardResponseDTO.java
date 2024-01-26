package com.example.DevSculpt.dto.board;

import com.example.DevSculpt.dto.comment.CommentResponseDTO;
import com.example.DevSculpt.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDTO {
    private Long boardId;
    private Long devId;
    private String title;
    private String content;
    private String category;
    private String nickName;
    private String profileImage;
    private int currentPage;
    private int totalPage;
    private List<CommentResponseDTO> comments;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

    public Long getUserId() {
        return this.devId;
    }
}
