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
@RequestMapping("/api") // TODO-1 done 너무 제너럴한 api 설계, api 룰을 어떻게 가져갈지 고민해야함 api/{version}/{domain} 등, naver, kakao api 참고하면 좋다.

public class ApiController {

    private final UserService service;
    @PostMapping("/create") // TODO-1 rest-api done rule 에 어긋남. 행동 -> HTTP method, POST, GET, PUT, DELETE, 자원(resource, 복수형)
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        service.createUser(requestDTO);
        return ResponseEntity.ok("성공");
    }

    @GetMapping("/find") // TODO-1 done rest-api rule 에 어긋남
    public ResponseEntity<UserResponseDTO> findUserByNicknameOrId(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "nickName", required = false) String nickName) {
        // 의도: 2개의 값중 하나, 또는 둘다 -> 둘다 안오면 안됨
        if(username == null && nickName == null){
            throw Error('두개의 값중 하나는 필수입니다.')
        }

            // TODO-1 done 좋지 않은 변수명, 그냥 user 라고 해도 될듯, 동사+명사 는 변수명으론 적합치 않다.
        UserResponseDTO finduser = service.findUserByIdAndNickName(username, nickName);
        return ResponseEntity.ok(finduser);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserResponseDTO responseDTO) {
        UserResponseDTO updatedUser = service.updateUser(id, responseDTO);
        if (updatedUser != null) { // TODO-1 done 부정문 사용X
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}