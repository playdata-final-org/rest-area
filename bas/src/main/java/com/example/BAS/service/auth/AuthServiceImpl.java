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
import java.nio.file.Files;
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

    @Value("${spring.servlet.multipart.location}")    //yml에 설정한 경로 생성
    private String uploadPath;

    @Transactional  //회원가입
    @Override
    public AuthDTO signup(SignupDTO signupDTO) throws IOException {
        //받아온 패스워드를 암호화해서 저장
        String rawPassword = signupDTO.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        signupDTO.setPassword(encPassword);

        //회원가입시 받아온 권한 저장
        Authority authority = getOrCreateAuthority(signupDTO.getAuthority());

        //ModelMapper로 변환해서 저장
        ModelMapper mapper = new ModelMapper();
        Users user = mapper.map(signupDTO, Users.class);
        user.setAuthority(authority);

        // 예외처리: 이미지가 null이 아니거나 비어있지 않을 시
        if (signupDTO.getProfileImage() != null && !signupDTO.getProfileImage().isEmpty()){
            //saveProfileImage메서드로 profileImage에 저장
            ProfileImage profileImage = saveProfileImage(signupDTO.getProfileImage());
            //그 후 User엔티티에 업데이트
            user.updateProfileImage(profileImage);
        }else {
            // 반대로 비어있거나 null인 상태이면 defaultProfileImage메서드로 디폴트 이미지를 저장
            ProfileImage defaultImage = defaultProfileImage();
            user.updateProfileImage(defaultImage);
        }
        // 정보들을 저장
        Users savedUser = userDAO.save(user);
        // AuthDTO를 생성하여 반환
        return mapper.map(savedUser, AuthDTO.class);
    }

    private ProfileImage saveProfileImage(MultipartFile file) throws IOException {
        // 이미지 파일이 아닌 경우 경고 메시지 출력
        try {
            //이미지가 null이거나 비었을시 처리
            if(file == null || file.isEmpty()){
                //defaultProfileImage 메서드에 반환
                return defaultProfileImage();
            }
            //이미지 타입이 image가 아닐시 에러 발생 => 컨트롤러랑 연계
            if(!file.getContentType().startsWith("image")){
                log.warn("이미지 파일이 아닙니다.");
                throw new IllegalArgumentException("이미지 파일이 아닙니다.");
            }

            String originalName = file.getOriginalFilename();
            Path root = Paths.get(uploadPath,"users");
            ImageDTO imageDTO = fileService.createImageDTO(originalName, root);
            ProfileImage profileImage = ProfileImage.builder()
                    .uuid(imageDTO.getUuid())
                    .fileName(imageDTO.getFileName())
                    .fileUrl(imageDTO.getFileUrl())
                    .build();
            file.transferTo(Paths.get(imageDTO.getFileUrl()));

            return profileImageRepository.save(profileImage);

        } catch (IOException e) {
            log.warn("업로드 폴더 생성 실패: " + e.getMessage());
            return null;
        }
    }

    private ProfileImage defaultProfileImage() throws IOException {
        //디폴트 이미지 생성 saveProfileImage메서드와 같은 방식임
        String defaultProfileImage = "ai.png";
        Path root = Paths.get(uploadPath, "users");
        ImageDTO imageDTO = fileService.createImageDTO(defaultProfileImage, root);

        return ProfileImage.builder()
                .uuid(imageDTO.getUuid())
                .fileName(imageDTO.getFileName())
                .fileUrl(imageDTO.getFileUrl())
                .build();
    }

    private byte[] getDefaultImage() throws IOException {
        //경로에 있는 지정한 디폴트 이미지를 바이트화 경로상에 지정한 이미지가 파일이 있어야함(ai.png)
        Path defaultImagePath = Paths.get("C:/back_msa/rest-area/bas/src/main/resources/static/assets/ai.png");
        return Files.readAllBytes(defaultImagePath);
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

    @Override //에러메시지
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
