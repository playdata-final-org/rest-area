package com.example.BAS.service.auth;

import com.example.BAS.dao.auth.AuthDAO;
import com.example.BAS.dao.blog.BlogDAO;
import com.example.BAS.dao.user.UserDAO;
import com.example.BAS.dto.auth.AuthDTO;
import com.example.BAS.dto.auth.SignupDTO;
import com.example.BAS.dto.auth.UpdateUserDTO;
import com.example.BAS.dto.file.ImageDTO;
import com.example.BAS.entitiy.authority.Authorities;
import com.example.BAS.entitiy.authority.UserStatus;
import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.entitiy.files.ProfileImage;
import com.example.BAS.entitiy.users.Users;
import com.example.BAS.repository.ProfileImageRepository;
import com.example.BAS.service.blog.BlogService;
import com.example.BAS.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {
    private final BlogService blogService;
    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final BlogDAO blogDAO;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FileService fileService;
    private final ProfileImageRepository profileImageRepository;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;


    @Override
    public AuthDTO signup(SignupDTO signupDTO) throws IOException {

        String rawPassword = signupDTO.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        signupDTO.setPassword(encPassword);

        Authorities authority = new Authorities();
        authority.setBoosterAuthority("ROLE_BOOSTER");
        authority.setCreatorAuthority("ROLE_CREATOR");

        ModelMapper mapper = new ModelMapper();
        Users user = mapper.map(signupDTO, Users.class);

        user.setCurrentRole("ROLE_BOOSTER");
        user.setUserState(true);

        UserStatus userStatus = new UserStatus();
        userStatus.setAuthorities(authority);
        userStatus.setUser(user);

        if (signupDTO.getProfileImage() != null && !signupDTO.getProfileImage().isEmpty()) {
            ProfileImage profileImage = saveProfileImage(signupDTO.getProfileImage());
            user.updateProfileImage(profileImage);
        } else {
            ProfileImage defaultImage = defaultProfileImage();
            user.updateProfileImage(defaultImage);
        }
        user.getUserStatuses().add(userStatus);
        Users savedUser = userDAO.save(user);

        AuthDTO authDTO = new AuthDTO();
        authDTO.setUserId(savedUser.getUserId());
        authDTO.setUsername(savedUser.getUsername());
        authDTO.setNickName(savedUser.getNickName());
        authDTO.setUserStatuses(savedUser.getUserStatuses());
        authDTO.setCurrentRole(savedUser.getCurrentRole());

        return authDTO;
    }


    private ProfileImage saveProfileImage(MultipartFile file) throws IOException {
        try {
            if (file == null || file.isEmpty()) {
                return defaultProfileImage();
            }
            if (!file.getContentType().startsWith("image")) {
                log.warn("이미지 파일이 아닙니다.");
                throw new IllegalArgumentException("이미지 파일이 아닙니다.");
            }

            String originalName = file.getOriginalFilename();
            Path root = Paths.get(uploadPath, "users");
            ImageDTO imageDTO = fileService.createImageDTO(originalName, root);

            String relativeFileUrl = "/img/users/" + imageDTO.getUuid() + "_" + imageDTO.getFileName();

            ProfileImage profileImage = ProfileImage.builder()
                    .uuid(imageDTO.getUuid())
                    .fileName(imageDTO.getFileName())
                    .fileUrl(relativeFileUrl)
                    .build();
            file.transferTo(Paths.get(imageDTO.getFileUrl()));

            return profileImageRepository.save(profileImage);

        } catch (IOException e) {
            log.warn("업로드 폴더 생성 실패: " + e.getMessage());
            return null;
        }
    }

    private ProfileImage defaultProfileImage() throws IOException {
        String defaultProfileImage = "ai.png";
        Path root = Paths.get(uploadPath, "users");
        ImageDTO imageDTO = fileService.createImageDTO(defaultProfileImage, root);

        String relativeFileUrl = "/img/users/ai.png";

        return ProfileImage.builder()
                .uuid(imageDTO.getUuid())
                .fileName(imageDTO.getFileName())
                .fileUrl(relativeFileUrl)
                .build();
    }

    private byte[] getDefaultImage() throws IOException {
        Path defaultImagePath = Paths.get("C:/back_msa/rest-area/bas/src/main/resources/static/assets/ai.png");
        return Files.readAllBytes(defaultImagePath);
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
        return existsByUsername ? "이미 사용 중인 이메일 입니다" : "사용 가능한 이메일 입니다.";
    }

    @Override
    public String checkDuplicateNickName(String nickName) {
        if (nickName == null || nickName.isEmpty()) {
            return "필수 입력값입니다.";
        }
        boolean existsByNickName = userDAO.existsByNickName(nickName);
        return existsByNickName ? "이미 사용 중인 닉네임" : "사용 가능한 닉네임 입니다.";
    }

    @Override
    public String checkDuplicatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return "필수 입력값입니다.";
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String storedEncodedPassword = userDetails.getPassword();

        if (bCryptPasswordEncoder.matches(password, storedEncodedPassword)) {
            return "패스워드가 일치합니다";
        } else {
            return "패스워드가 일치하지 않습니다.";
        }
    }

    @Override
    public boolean isUsernameDuplicate(String username) {
        return userDAO.existsByUsername(username);
    }

    @Override
    public boolean isNickNameDuplicate(String nickName) {
        return userDAO.existsByNickName(nickName);
    }

    @Override
    public AuthDTO updateUser(UpdateUserDTO updateUserDTO, String oldPassword) throws IOException {

        Users userToUpdate = userDAO.findByUserId(updateUserDTO.getUserId());
        ModelMapper mapper = new ModelMapper();

        if (userToUpdate != null) {
            if (bCryptPasswordEncoder.matches(oldPassword, userToUpdate.getPassword())) {

                String rawNewPassword = updateUserDTO.getNewPassword();
                String encNewPassword = bCryptPasswordEncoder.encode(rawNewPassword);
                userToUpdate.setPassword(encNewPassword);

                deleteProfileImage(userToUpdate.getProfileImage());

                if (updateUserDTO.getProfileImage() != null && !updateUserDTO.getProfileImage().isEmpty()) {
                    ProfileImage profileImage = saveProfileImage(updateUserDTO.getProfileImage());
                    userToUpdate.updateProfileImage(profileImage);
                } else {
                    ProfileImage defaultImage = defaultProfileImage();
                    userToUpdate.updateProfileImage(defaultImage);
                }
                String nickName = updateUserDTO.getNickName();
                userToUpdate.setNickName(nickName);
                Users savedUser = userDAO.save(userToUpdate);
                return mapper.map(savedUser, AuthDTO.class);
            }
        }
        return null;
    }

    public AuthDTO switchToCreator(Long userId) {
        Users user = userDAO.findByUserId(userId);
        ModelMapper mapper = new ModelMapper();
        if ("ROLE_BOOSTER".equals(user.getCurrentRole())) {
            Authorities creatorAuthority = authDAO.findByCreatorAuthority("ROLE_CREATOR",userId);
            System.out.println("creatorAuthority = " + creatorAuthority);

            if (creatorAuthority != null) {
                user.setCurrentRole("ROLE_CREATOR");
                Users savedUser = userDAO.save(user);
                List<Blogs> userBlogs = blogDAO.findByUsers(savedUser);

                if (userBlogs.isEmpty()) {
                    blogService.saveBlog(savedUser);
                }

                return mapper.map(savedUser, AuthDTO.class);
            } else {
                throw new RuntimeException("잘못된 권한 전환입니다.");
            }
        } else {
            throw new RuntimeException("잘못된 권한 전환입니다.");
        }
    }

    public AuthDTO switchToBooster(Long userId) {
        Users user = userDAO.findByUserId(userId);
        ModelMapper mapper = new ModelMapper();
        if ("ROLE_CREATOR".equals(user.getCurrentRole())) {
            Authorities boosterAuthority = authDAO.findByBoosterAuthority("ROLE_BOOSTER",userId);
            if (boosterAuthority != null) {
                user.setCurrentRole("ROLE_BOOSTER");
                Users savedUser = userDAO.save(user);

                return mapper.map(savedUser, AuthDTO.class);
            } else {
                throw new RuntimeException("잘못된 권한 전환입니다.");
            }
        }else{
            throw new RuntimeException("잘못된 권한 전환입니다.");
        }

    }

    @Override
    public Users findByUserId(Long userId) {
        return userDAO.findByUserId(userId);
    }

    @Override
    public Long getBlogIdByUserId(Long userId) {
        return userDAO.getBlogIdByUserId(userId);
    }

    private void deleteProfileImage(ProfileImage profileImage) {
        if (profileImage != null) {
            try {
                String imagePath = profileImage.getFileUrl();
                Path path = Paths.get(imagePath);

                Files.deleteIfExists(path);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}





