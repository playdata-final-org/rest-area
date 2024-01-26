package com.example.BAS.dto.auth;

import com.example.BAS.entitiy.authority.UserStatus;
import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.entitiy.files.ProfileImage;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//UserResponseDTO
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDTO {
    private Long userId;
    private ProfileImage profileImage;
    private String username;
    private String password;
    private String nickName;
    private List<UserStatus> userStatuses;
    private List<Blogs> blogs;
    private String currentRole;
}
