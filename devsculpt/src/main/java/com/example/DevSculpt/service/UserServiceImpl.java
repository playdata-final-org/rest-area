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
        userentity.setPassword(passwordEncoder.encode(requestDTO.getPassword())); // TODO-1 암호화 2way 인지, ?
        // 회원가입시, 기본 권한은 USER로 준다.
        userentity.setRole("ROLE_USER"); // TODO-1 권한을 다르게 주고 싶으면
        UserEntity savedEntity = dao.save(userentity);
        return mapper.map(savedEntity, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserResponseDTO responseDTO) {
        UserEntity existingEntity = dao.findById(id);
        if (existingEntity == null) { // TODO-1 data가 없을때 null return 만으로 충분할지?
            return null;
        }

        // 업데이트 동일하게 passwordEncoder를 사용해서 복호화 작업을 처리
        existingEntity.setPassword(passwordEncoder.encode(responseDTO.getPassword()));
        existingEntity.setNickName(responseDTO.getNickName());
        // 업데이트 날짜, 자동 추가
        existingEntity.setRevisionDate(LocalDateTime.now()); // TODO-1 DB 상에서 on update default time set 을 하는건 어떨지?

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

    @Override
    public boolean idcheck(String username) { // TODO-1 동사+명사 형태가 기본
        return dao.idcheck(username);
    }
}
