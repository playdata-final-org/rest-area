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
@RequestMapping("/api") // TODO-1 너무 제너럴한 api 설계, api 룰을 어떻게 가져갈지 고민해야함 api/{version}/{domain} 등, naver, kakao api 참고하면 좋다.

public class ApiController {

    private final UserService service;

    @PostMapping("/create") // TODO-1 rest-api rule 에 어긋남.
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        service.createUser(requestDTO);
        return ResponseEntity.ok("성공");
    }

    @GetMapping("/find") // TODO-1 rest-api rule 에 어긋남
    public ResponseEntity<UserResponseDTO> findUserByNicknameOrId(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "nickName", required = false) String nickName) {
        if (username != null || nickName != null) { // TODO-1 early return 형태로 방어하는 것도 좋아보임
            // TODO-1 좋지 않은 변수명, 그냥 user 라고 해도 될듯, 동사+명사 는 변수명으론 적합치 않다.
            UserResponseDTO finduser = service.findUserByIdAndNickName(username, nickName);
            return ResponseEntity.ok(finduser);
        } else {
            // TODO-1 어떠한 의도의 비즈니스 로직인지 모르겠음, null 을 return 할 거면 아예 처음부터 호출을 막아버리는 형태가 낫지 않은지(왜 필요한 param 이 모두 not required 인지 모르겠음)
            return null;
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserResponseDTO responseDTO) {
        UserResponseDTO updatedUser = service.updateUser(id, responseDTO);
        if (updatedUser != null) { // TODO-1 early return
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}