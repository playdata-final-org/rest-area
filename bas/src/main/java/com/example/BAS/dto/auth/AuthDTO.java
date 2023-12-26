package com.example.BAS.dto.auth;

import com.example.BAS.entitiy.authority.Authority;
import com.example.BAS.entitiy.image.ProfileImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//UserResponseDTO
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDTO {
    private Long userId;
    private ProfileImage profileImage;
    private String username;
    private String password;
    private String email;
    private String authority;
}
