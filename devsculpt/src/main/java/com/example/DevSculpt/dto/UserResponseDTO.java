package com.example.DevSculpt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
// 회원가입 반환

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String nickName;
    private String email;
    private String role;
}



