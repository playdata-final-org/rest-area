package com.example.DevSculpt.service;

import com.example.DevSculpt.dao.UserDAO;
import com.example.DevSculpt.dto.UserRequestDTO;
import com.example.DevSculpt.dto.UserResponseDTO;
import com.example.DevSculpt.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDAO dao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        // Model Mapper 를 사용해서 DTO -> 엔티티 변환 작업
        ModelMapper mapper = new ModelMapper();
        UserEntity userentity = mapper.map(requestDTO, UserEntity.class);
        // 비밀번호 복호화 작업
        userentity.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        // 회원가입시, 기본 권한은 USER로 준다.
        userentity.setRole("ROLE_USER");
        UserEntity savedEntity = dao.save(userentity);
        return mapper.map(savedEntity, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserResponseDTO responseDTO) {
        UserEntity existingEntity = dao.findById(id);
        if (existingEntity == null) {
            return null;
        }
        // 업데이트 동일하게 passwordEncoder를 사용해서 복호화 작업을 처리
        existingEntity.setPassword(passwordEncoder.encode(responseDTO.getPassword()));
        existingEntity.setNickName(responseDTO.getNickName());
        // 업데이트 날짜, 자동 추가
        existingEntity.setRevisionDate(LocalDateTime.now());

        UserEntity updatedEntity = dao.save(existingEntity);
        ModelMapper mapper = new ModelMapper();
        return mapper.map(updatedEntity, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO findUserByIdAndNickName(String username, String nickName) {
        ModelMapper mapper = new ModelMapper();
        UserEntity userEntity = dao.findByUsernameOrNickName(username, nickName);
        if (userEntity != null) {
            return mapper.map(userEntity, UserResponseDTO.class);
        }
        return null;
    }


}
