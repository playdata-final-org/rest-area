package com.example.DevSculpt.dto.user;

import com.example.DevSculpt.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
// 회원가입 반환

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO implements Serializable {
    private static final long serialVersionUID = -9223365651070458532L;

    private Long devId;
    private String username;
    private String password;
    private String name;
    private String nickName;
    private String email;
    private String role;
    private UserEntity userEntity;
    private String profileImage;
}



