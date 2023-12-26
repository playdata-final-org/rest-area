package com.example.BAS.service.auth;

import com.example.BAS.dao.auth.AuthDAO;
import com.example.BAS.dao.user.UserDAO;
import com.example.BAS.dto.auth.AuthDTO;
import com.example.BAS.dto.auth.SignupDTO;
import com.example.BAS.dto.file.ImageDTO;
import com.example.BAS.entitiy.authority.Authority;
import com.example.BAS.entitiy.image.ProfileImage;
import com.example.BAS.entitiy.users.Users;
import com.example.BAS.repository.ProfileImageRepository;
import com.example.BAS.service.file.FileService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FileService fileService;
    private final ProfileImageRepository profileImageRepository;
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Transactional
    @Override
    public AuthDTO signup(SignupDTO signupDTO) {

        // 비밀번호 해싱
        String rawPassword = signupDTO.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        signupDTO.setPassword(encPassword);
        Authority authority = getOrCreateAuthority(signupDTO.getAuthority());

        //ModelMapper로 변환해서 저장
        ModelMapper mapper = new ModelMapper();
        Users users = mapper.map(signupDTO, Users.class);
        users.setAuthority(authority);
        Users user = userDAO.save(users);

        if (!(signupDTO.getProfileImage() == null)){
            ProfileImage profileImage = saveProfileImage(signupDTO.getProfileImage());
            users.updateProfileImage(profileImage);
        }

        return mapper.map(user, AuthDTO.class);
    }
    private ProfileImage saveProfileImage(MultipartFile file){
        if(!file.getContentType().startsWith("image")){
            log.warn("이미지 파일이 아닙니다.");
            return null;
        }
        String originalName = file.getOriginalFilename();
        Path root = Paths.get(uploadPath, "member");

        try{
            ImageDTO imageDTO = fileService.createImageDTO(originalName, root);
            ProfileImage profileImage = ProfileImage.builder()
                    .uuid(imageDTO.getUuid())
                    .fileName(imageDTO.getFileName())
                    .fileUrl(imageDTO.getFileUrl())
                    .build();

            file.transferTo(Paths.get(imageDTO.getFileUrl()));
            return  profileImageRepository.save(profileImage);

        } catch (IOException e) {
            log.warn("업로드 폴더 생성 실패: " + e.getMessage());
        }
        return  null;
    }


    private Authority getOrCreateAuthority(String authorityName) {
        // 권한 이름으로 조회
        Authority authority = authDAO.findByAuthorityName(authorityName);

        // 권한이 없으면 새로 생성
        if (authority == null) {
            authority = new Authority();
            authority.setAuthorityName(authorityName);
            authority = authDAO.save(authority);
        }

        return authority;
    }


    @Override
    public void logout(HttpSession session) {

    }

    @Override
    public Map<String, String> errorMessage(Errors errors) {
        Map<String, String> result = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String key = String.format("error_%s", error.getField());
            result.put(key, error.getDefaultMessage());
        }


        return result;
    }

    @Override
    public String checkDuplicateUsername(String username) {
        if (username == null || username.isEmpty()) {
            return "필수 입력값입니다.";
        }
        boolean existsByUsername = userDAO.existsByUsername(username);
        return existsByUsername ? "이미 사용 중인 아이디 입니다" : "사용 가능한 아이디 입니다.";
    }

    @Override
    public String checkDuplicateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return "필수 입력값입니다.";
        }
        boolean existsByEmail = userDAO.existsByEmail(email);
        return existsByEmail ? "이미 사용 중인 이메일 입니다" : "사용 가능한 이메일 입니다.";
    }

    @Override
    public boolean isUsernameDuplicate(String username) {
        return userDAO.existsByUsername(username);
    }

    @Override
    public boolean isEmailDuplicate(String email) {
        return userDAO.existsByEmail(email);
    }


}
