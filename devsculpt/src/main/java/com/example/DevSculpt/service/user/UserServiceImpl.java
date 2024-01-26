package com.example.DevSculpt.service.user;

import com.example.DevSculpt.dao.user.UserDAO;
import com.example.DevSculpt.dto.user.UserProfileRequestDTO;
import com.example.DevSculpt.dto.user.UserRequestDTO;
import com.example.DevSculpt.dto.user.UserResponseDTO;
import com.example.DevSculpt.entity.UserEntity;
import com.example.DevSculpt.service.file.FileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDAO dao;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    @Value("${upload.path}")
    private String uploadPath;

    // Default 프로필 이미지 경로
    private static final String DEFAULT_PROFILE_IMAGE_PATH = "/assets/image/defaultprofile.png";

    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        ModelMapper mapper = new ModelMapper();
        UserEntity userEntity = mapper.map(requestDTO, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        userEntity.setRole("ROLE_USER");
        UserEntity savedEntity = dao.save(userEntity);
        return mapper.map(savedEntity, UserResponseDTO.class);
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(UserProfileRequestDTO requestDTO) {
        UserEntity existingEntity = dao.findById(requestDTO.getDevId());
        // 닉네임 변경
        if (requestDTO.getNickName() != null && !requestDTO.getNickName().isEmpty()) {
            existingEntity.setNickName(requestDTO.getNickName());
        }

        // 프로필 이미지 변경
        MultipartFile profileImage = requestDTO.getProfileImage();
        if (profileImage != null && !profileImage.isEmpty()) {
            String fileName = fileService.saveProfileImage(profileImage, existingEntity.getDevId());
            existingEntity.setProfileImage(fileName);
        }

        existingEntity.setModificationDate(LocalDateTime.now());

        // 엔티티 저장
        UserEntity updatedEntity = dao.save(existingEntity);

        // ModelMapper를 사용하여 엔티티를 DTO로 매핑
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
    public boolean checkId(String username) {
        return dao.idcheck(username);
    }

    @Override
    public void setUserDefaultProfileImage(UserEntity userEntity) {
        userEntity.setProfileImage(DEFAULT_PROFILE_IMAGE_PATH);
    }

    @Override
    public UserResponseDTO findByUser(Long devId) {
        ModelMapper mapper = new ModelMapper();
        UserEntity userEntity = dao.findById(devId);
        if (userEntity != null) {
            return mapper.map(userEntity, UserResponseDTO.class);
        }
        return null;
    }

    @Override
    public void deleteUser(Long devId) {
        dao.deleteUser(devId);
    }
}