package com.example.DevSculpt.controller.user;

import com.example.DevSculpt.dto.LoginRequestDTO;
import com.example.DevSculpt.dto.UserRequestDTO;
import com.example.DevSculpt.service.UserService;
import com.example.DevSculpt.service.security.DevUserDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        boolean checkid = userService.idcheck(userRequestDTO.getUsername());
        if (checkid) {
            return "user/sign-up";
        }
        userService.createUser(userRequestDTO);
        return "main/index";
    }

    @GetMapping("/login")
    public String login(String username, String password) {
        return "user/login";
    }

    @GetMapping("/checkUsernameAvailability")
    public ResponseEntity<Map<String, Boolean>> checkUsernameAvailability(@RequestParam String username) {
        Map<String, Boolean> response = new HashMap<>();
        boolean isAvailable = !userService.idcheck(username);
        response.put("available", isAvailable);
        return ResponseEntity.ok(response);
    }
}