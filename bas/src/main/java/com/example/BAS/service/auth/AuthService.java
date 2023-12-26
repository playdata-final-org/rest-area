package com.example.BAS.service.auth;

import com.example.BAS.dto.auth.AuthDTO;
import com.example.BAS.dto.auth.SignupDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.validation.Errors;

import java.util.Map;

public interface AuthService {
    AuthDTO signup(SignupDTO signupDTO);
    void logout(HttpSession session);
    Map<String, String> errorMessage(Errors errors);
    String checkDuplicateUsername(String username);
    String checkDuplicateEmail(String email);
    boolean isUsernameDuplicate(String username);
    boolean isEmailDuplicate(String email);
}
