package com.example.DevSculpt.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// 회원가입

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    // 회원 아이디
    private String username;
    // 회원 비밀번호
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "회원가입시 패스워드는 특수문자,대문자 포함 8글자 이상이어야 합니다."
    )
    private String password;
    // 회원 이름 (실명)
    private String name;
    // 회원 닉네임
    private String nickName;
    // 회원 이메일
    private String email;
}
