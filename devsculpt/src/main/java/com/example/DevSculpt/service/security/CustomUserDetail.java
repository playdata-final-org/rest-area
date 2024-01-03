package com.example.DevSculpt.service.security;

import com.example.DevSculpt.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetail extends User {
    private final UserResponseDTO userResponseDTO;

    public CustomUserDetail(UserResponseDTO userResponseDTO, Collection<? extends GrantedAuthority> authorities) {
        super(userResponseDTO.getUsername(), userResponseDTO.getPassword(), authorities);
        this.userResponseDTO = userResponseDTO;
    }

    public UserResponseDTO getUserResponseDTO() {
        return userResponseDTO;
    }
}