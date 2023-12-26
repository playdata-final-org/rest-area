package com.example.DevSculpt.controller;

import com.example.DevSculpt.dto.UserRequestDTO;
import com.example.DevSculpt.dto.UserResponseDTO;
import com.example.DevSculpt.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")

public class ApiController {

    private final UserService service;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        service.createUser(requestDTO);
        return ResponseEntity.ok("성공");
    }

    @GetMapping("/find")
    public ResponseEntity<UserResponseDTO> findUserByNicknameOrId(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "nickName", required = false) String nickName) {
        if (username != null || nickName != null) {
            UserResponseDTO finduser = service.findUserByIdAndNickName(username, nickName);
            return ResponseEntity.ok(finduser);
        } else {
            return null;
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserResponseDTO responseDTO) {
        UserResponseDTO updatedUser = service.updateUser(id, responseDTO);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}