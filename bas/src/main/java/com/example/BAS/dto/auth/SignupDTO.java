package com.example.BAS.dto.auth;

import com.example.BAS.entitiy.authority.Authority;
import com.example.BAS.entitiy.users.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

//회원가입 정보에 필요한 DTO = UserRequestDTO
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignupDTO {

    private Long userId;
    private MultipartFile profileImage;

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String username;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$",
            message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    private String authority;





}
