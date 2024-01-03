package com.example.DevSculpt.repository;

import com.example.DevSculpt.dto.UserResponseDTO;
import com.example.DevSculpt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // 아이디로 검색 , 닉네임으로 검색
    UserEntity findByUsernameOrNickName(String username, String nickName);

    // Spring security loadUserByUsername
    UserEntity findByUsername(String username);

    // 아이디 중복 유효성 체크
    boolean existsByUsername(String username);
}
