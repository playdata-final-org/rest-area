package com.example.DevSculpt.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRequestDTO {
    private Long devId;
    private String nickName;
    private MultipartFile profileImage;
}
