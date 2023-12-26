package com.example.DevSculpt.service.security;

import com.example.DevSculpt.dto.UserResponseDTO;
import com.example.DevSculpt.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetail extends User {

    public CustomUserDetail(UserResponseDTO entity, Collection<? extends GrantedAuthority> authorities) {
        super(entity.getUsername(), entity.getPassword(), authorities);
    }
}
