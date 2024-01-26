package com.example.DevSculpt.service.security;

import com.example.DevSculpt.dto.user.UserResponseDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetail extends User {
    private UserResponseDTO userResponseDTO;

    public CustomUserDetail(UserResponseDTO userResponseDTO, Collection<? extends GrantedAuthority> authorities) {
        super(userResponseDTO.getUsername(), userResponseDTO.getPassword(), authorities);
        this.userResponseDTO = userResponseDTO;
    }

    public UserResponseDTO getUserResponseDTO() {
        return userResponseDTO;
    }

    // 닉네임 가져오는 메서드 추가
    public String getNickname() {
        return userResponseDTO.getNickName();
    }

    // 프로필 이미지 가져오는 메서드 추가
    public String getProfileImage() {
        return userResponseDTO.getProfileImage();
    }

    public Long getUserId() {
        return userResponseDTO.getDevId();
    }
}