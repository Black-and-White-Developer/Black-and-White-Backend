package bw.growingcode.user.controller;

import bw.growingcode.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "카카오 로그인 redirect uri",
        description = "카카오 로그인 후 인증 결과를 받아 처리하기 위한 uri로 프론트에서 호출 x")
    @GetMapping("/login/oauth2/code/kakao")
    public ResponseEntity<Void> kakaoLoginCallback(@RequestParam String code) {
        //onAuthenticationSuccess 에서 처리
        return ResponseEntity.ok().build();
    }
}

