package com.example.BAS.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//로그인에 필요한 DTO
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SigninDTO {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
