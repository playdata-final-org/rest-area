package com.example.BAS.service.auth;

import com.example.BAS.dto.auth.AuthDTO;
import com.example.BAS.dto.auth.SignupDTO;
import com.example.BAS.dto.auth.UpdateUserDTO;
import com.example.BAS.entitiy.users.Users;
import org.springframework.validation.Errors;

import java.io.IOException;
import java.util.Map;

public interface AuthService {
    AuthDTO signup(SignupDTO signupDTO) throws IOException;
    Map<String, String> errorMessage(Errors errors);
    String checkDuplicateUsername(String username);
    String checkDuplicateNickName(String nickName);
    String checkDuplicatePassword(String password);
    boolean isUsernameDuplicate(String username);
    boolean isNickNameDuplicate(String nickName);
    AuthDTO updateUser(UpdateUserDTO updateUserDTO, String password) throws IOException;
    AuthDTO switchToCreator(Long userId);
    AuthDTO switchToBooster(Long userId);
    Users findByUserId(Long userId);
    Long getBlogIdByUserId(Long userId);
}
