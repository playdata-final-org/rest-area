package com.example.DevSculpt.dto.comment;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDTO {
    private String nickName;
    private String content;
}
