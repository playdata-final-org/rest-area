package com.example.DevSculpt.dto.board;

import com.example.DevSculpt.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequestDTO {
    private String title;
    private String content;
    private String category;
    private Long devId;
    private List<MultipartFile> files;
}
