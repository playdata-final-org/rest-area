package com.example.DevSculpt.controller.user;

import com.example.DevSculpt.dto.user.UserProfileRequestDTO;
import com.example.DevSculpt.dto.user.UserRequestDTO;
import com.example.DevSculpt.dto.user.UserResponseDTO;
import com.example.DevSculpt.service.user.UserService;
import com.example.DevSculpt.service.security.DevUserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/dev")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final DevUserDetailService devUserDetailService;

    @GetMapping("/signup")
    public String sign(Model model) {
        model.addAttribute("userRequestDTO", new UserRequestDTO());
        return "user/sign-up";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("userRequestDTO") @Valid UserRequestDTO userRequestDTO) {
        boolean checkid = userService.checkId(userRequestDTO.getUsername());
        if (checkid) {
            return "user/sign-up";
        }
        userService.createUser(userRequestDTO);
        return "redirect:/dev/index";
    }

    @GetMapping("/login")
    public String login(String username, String password) {
        return "user/login";
    }

    @GetMapping("/checkUsernameAvailability")
    public ResponseEntity<Map<String, Boolean>> checkUsernameAvailability(
            @RequestParam(name = "username", required = false) String username) {
        boolean isAvailable = userService.checkId(username);
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", isAvailable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/mypage/user/{devId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String getMyPage(@PathVariable Long devId, Model model) {
        UserResponseDTO userResponseDTO = userService.findByUser(devId);
        if (userResponseDTO != null) {
            model.addAttribute("user", userResponseDTO);
            return "user/mypage";
        } else {
            return null;
        }
    }

    @PostMapping("/updateuser/{devId}")
    public String updateUser(@PathVariable Long devId, UserProfileRequestDTO requestDTO, RedirectAttributes redirectAttributes) {
        requestDTO.setDevId(devId);

        UserResponseDTO updatedUser = userService.updateUser(requestDTO);

        if (updatedUser != null) {
            redirectAttributes.addFlashAttribute("message", "프로필이 성공적으로 업데이트되었습니다.");
            return "redirect:/dev/index"; // 업데이트 후에는 메인 페이지로 리다이렉트
        } else {
            redirectAttributes.addFlashAttribute("error", "프로필 업데이트에 실패했습니다.");
            return "redirect:main/index";
        }
    }

    @GetMapping("/account/{devId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String getAccountPage(@PathVariable Long devId, Model model) {
        UserResponseDTO userResponseDTO = userService.findByUser(devId);
        if (userResponseDTO != null) {
            model.addAttribute("user", userResponseDTO);
            return "user/account";
        } else {
            return "redirect:/dev/index";
        }
    }

    @PostMapping("/deleteAccount/{devId}")
    public String deleteAccount(@PathVariable Long devId, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(devId);

            // 로그아웃 처리
            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(request, response, null);

            return "redirect:/dev/index"; // 계정 삭제 후에는 메인 페이지로 리다이렉트
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "계정 삭제 중 오류가 발생했습니다.");
            return "redirect:/dev/account/" + devId;
        }
    }
}
