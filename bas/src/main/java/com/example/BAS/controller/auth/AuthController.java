package com.example.BAS.controller.auth;

import com.example.BAS.config.security.PrincipalDetails;
import com.example.BAS.dto.auth.SignupDTO;
import com.example.BAS.entitiy.image.ProfileImage;
import com.example.BAS.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

//로그인,회원가입,권한 관련
@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @GetMapping("/main") //메인페이지
    public String mainPage() {
        return "user/main";
    }

    @GetMapping("/test") //로그인 성공 후 test페이지 -> 후 크리에이터:블로그 후원자:크리에이터 찾기
    public String loginPage(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        if ("ROLE_BOOSTER".equals(principalDetails.getUsers().getAuthority().getAuthorityName())) {
            return "user/test";
        } else {
            return "user/main";
        }
    }

    @GetMapping("/signin") //로그인 페이지
    public String signForm() {
        return "user/signin";
    }

    @GetMapping("/signup") //회원가입 페이지
    public String signupForm(Model model) {
        SignupDTO signupDTO = new SignupDTO();
        //모델에 SignupDTO를 담아서 전달
        model.addAttribute("signupDTO", signupDTO);

        return "user/signup";
    }

    @PostMapping("/signup") //회원가입
    public String signup(@Valid SignupDTO signupDTO, Errors errors, Model model,
                         @RequestParam("file") MultipartFile file) throws IOException {
        try {
                // 유효성 검사 - 에러 유무 확인
            if (errors.hasErrors()) {
                // 모델에 SignupDTO와 유효성 검사 결과를 담아서 전달
                model.addAttribute("signupDTO", signupDTO);
                // 유효성 검사 결과를 Map으로 받아옴
                Map<String, String> validationErrors = authService.errorMessage(errors);
                log.info("Validator Result: {}", validationErrors);
                // 각 유효성 검사 결과를 모델에 추가
                for (String key : validationErrors.keySet()) {
                    model.addAttribute(key, validationErrors.get(key));
                }
                return "user/signup";
            }
            // 중복 검사 - 아이디 중복 또는 이메일 중복이 있는지 확인
            boolean isUsernameDuplicate = authService.isUsernameDuplicate(signupDTO.getUsername());
            boolean isEmailDuplicate = authService.isEmailDuplicate(signupDTO.getEmail());
            // 중복이 발생한 경우 회원가입 폼으로 돌아가기
            if (isUsernameDuplicate || isEmailDuplicate) {
                model.addAttribute("usernameDuplicate", isUsernameDuplicate);
                model.addAttribute("emailDuplicate", isEmailDuplicate);
                model.addAttribute("signupError", "중복 체크를 해주세요.");
                return "user/signup";
            }
            //프로필 이미지 저장
            authService.saveProfileImage(file);
            //프로필 이미지 처리중 Exception 발생 시 ErrorMessage를 모델에 담아 뷰로 보냄
            } catch (IllegalArgumentException e) {
                model.addAttribute("imageError", "이미지 파일이 아닙니다.");
                return "user/signup";
        }
        // 유효성 검사 및 중복 검사에 모두 통과한 경우 회원 가입 진행
        log.info("SignupDTO: {}", signupDTO);
        signupDTO.setProfileImage(file);
        log.info("SignupDTO: {}", signupDTO);
        authService.signup(signupDTO);
        log.info("SignupDTO: {}", signupDTO);
        return "user/signin";
    }
    @PostMapping("/checkDuplicateUsername")         //유저 네임 중복 검사
    @ResponseBody
    public String checkDuplicateUsername(@RequestParam String username) {
        return authService.checkDuplicateUsername(username);
    }
    @PostMapping("/checkDuplicateEmail")            //이메일 중복 검사
    @ResponseBody
    public String checkDuplicateEmail(@RequestParam String email) {
        return authService.checkDuplicateEmail(email);
    }
}
