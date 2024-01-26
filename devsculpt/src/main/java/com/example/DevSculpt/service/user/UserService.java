package com.example.DevSculpt.service.user;

import com.example.DevSculpt.dto.user.UserProfileRequestDTO;
import com.example.DevSculpt.dto.user.UserRequestDTO;
import com.example.DevSculpt.dto.user.UserResponseDTO;
import com.example.DevSculpt.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    // 회원 생성
    UserResponseDTO createUser(UserRequestDTO requestDTO);

    // 회원 업데이트
    UserResponseDTO updateUser(UserProfileRequestDTO requestDTO);

    // 회원 아이디, 닉네임으로 조회하기
    UserResponseDTO findUserByIdAndNickName(String username, String nickName);

    // 회원 아이디 중복 검사
    boolean checkId(String username);

    // 회원 기본 프로필
    void setUserDefaultProfileImage(UserEntity userEntity);

    // 회원 프로필 조회
    UserResponseDTO findByUser(Long devId);

    // 회원 탈퇴
    void deleteUser(Long devId);
}
