package com.example.DevSculpt.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponseDTO {
    private Long devId;
    private String name;
    private String nickName;
    private String profileImage;
}
