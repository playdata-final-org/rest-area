package com.example.BAS.controller.auth;

import com.example.BAS.config.security.PrincipalDetails;
import com.example.BAS.dto.auth.SignupDTO;
import com.example.BAS.dto.auth.UpdateUserDTO;
import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.entitiy.users.Users;
import com.example.BAS.service.auth.AuthService;
import com.example.BAS.service.blog.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final BlogService blogService;
    @GetMapping("/main")
    public String showMainPage() {
        return "user/main";
    }
    @GetMapping("/signin")
    public String showSignPage(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "exception", required = false) String exception,
                           Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "user/signin";
    }

    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        SignupDTO signupDTO = new SignupDTO();
        model.addAttribute("signupDTO", signupDTO);

        return "user/signup";
    }
    @PostMapping("/signup")
    public String signup(@Valid SignupDTO signupDTO, Errors errors, Model model,
                         @RequestParam("file") MultipartFile file) throws IOException {
        try {
            if (errors.hasErrors()) {
                model.addAttribute("signupDTO", signupDTO);
                Map<String, String> validationErrors = authService.errorMessage(errors);
                for (String key : validationErrors.keySet()) {
                    model.addAttribute(key, validationErrors.get(key));
                }
                return "user/signup";
            }
            boolean isUsernameDuplicate = authService.isUsernameDuplicate(signupDTO.getUsername());
            boolean isNickNameDuplicate = authService.isNickNameDuplicate(signupDTO.getNickName());

            if (isUsernameDuplicate || isNickNameDuplicate) {
                model.addAttribute("usernameDuplicate", isUsernameDuplicate);
                model.addAttribute("nickNameDuplicate", isNickNameDuplicate);
                model.addAttribute("signupError", "중복 체크를 해주세요.");
                return "user/signup";
            }

        } catch (IllegalArgumentException e) {
            model.addAttribute("imageError", "이미지 파일이 아닙니다.");
            return "user/signup";
        }
        signupDTO.setProfileImage(file);
        authService.signup(signupDTO);
        return "user/signin";
    }
    @GetMapping("/myPage/{userId}")
    public String showMyPage(@PathVariable("userId") Long userId,
                            @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        String rolePage = principalDetails.rolePage();

        Blogs blog = blogService.findByUserId(userId);
        Users user = principalDetails.getUsers();

        if (rolePage == null) {
            model.addAttribute("profileImageUrl",principalDetails.profileImageUrl());
            model.addAttribute("blog",blog);
            model.addAttribute("booster", principalDetails.getUsers());
            model.addAttribute("updateUserDTO", user);

            return "user/mypage";
        } else {
            return "user/main";
        }
    }
    @PostMapping("/myPage/{userId}")
    public String myPage(@PathVariable("userId") Long userId,
                         @ModelAttribute UpdateUserDTO updateUserDTO,
                         @RequestParam("file") MultipartFile newProfileImage,
                         @RequestParam("oldPassword") String oldPassword,
                         @AuthenticationPrincipal PrincipalDetails principalDetails,
                         Model model
                         ) throws IOException {
        updateUserDTO.setProfileImage(newProfileImage);

        authService.updateUser(updateUserDTO, oldPassword);
        String rolePage = principalDetails.rolePage();
        Blogs blog = blogService.findByUserId(userId);
        if (rolePage == null) {
            model.addAttribute("profileImageUrl", principalDetails.profileImageUrl());
            model.addAttribute("booster", principalDetails.getUsers());
            model.addAttribute("updateUserDTO", new UpdateUserDTO());
            model.addAttribute("blog",blog);

            return "redirect:/boostedHistory/"+userId;
        } else {
            return "user/main";
        }
    }

    @GetMapping("/transCreator")
    public String transCreator(@RequestParam("userId") Long userId,
                               @AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        authService.switchToCreator(userId);
        Long blogId = blogService.findBlogIdByUserId(userId);

        Users updatedUser = authService.findByUserId(userId);
        principalDetails.sessionReset(updatedUser);

        return "redirect:/blog/"+blogId+"?userId="+userId;
    }
    @GetMapping("/transBooster")
    public String transBooster(@RequestParam("userId") Long userId,
                               @AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        authService.switchToBooster(userId);
        Users updatedUser = authService.findByUserId(userId);
        principalDetails.sessionReset(updatedUser);

        return "redirect:/creator-search";
    }
    @PostMapping("/signup/checkDuplicatePassword")
    @ResponseBody
    public String checkDuplicatePassword(@RequestParam String password) {
        return authService.checkDuplicatePassword(password);
    }
    @PostMapping("/signup/checkDuplicateUsername")
    @ResponseBody
    public String checkDuplicateUsername(@RequestParam String username) {
        return authService.checkDuplicateUsername(username);
    }
    @PostMapping("/signup/checkDuplicateNickName")
    @ResponseBody
    public String checkDuplicateEmail(@RequestParam String nickName) {
        return authService.checkDuplicateNickName(nickName);
    }
}
