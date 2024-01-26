package com.example.DevSculpt.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {
    private Long commentId;
    private Long boardId;
    private String profileImage;
    private String nickName;
    private String content;
    private LocalDateTime creationDate;
}
