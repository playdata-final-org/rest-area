package com.example.DevSculpt.service.security;

import com.example.DevSculpt.dao.UserDAO;
import com.example.DevSculpt.dto.UserResponseDTO;
import com.example.DevSculpt.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DevUserDetailService implements UserDetailsService {
    private final UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = userDAO.login(username);
        if (entity == null) {
            throw new UsernameNotFoundException("사용자가 없습니다.");
        }
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserResponseDTO userResponseDTO = mapToUserResponseDTO(entity);
        CustomUserDetail userDetail = new CustomUserDetail(userResponseDTO, roles);
        return userDetail;
    }

    private UserResponseDTO mapToUserResponseDTO(UserEntity entity) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(entity, UserResponseDTO.class);
    }
}
