package bw.growingcode.user.controller;

import bw.growingcode.code.enums.GeminiType;
import bw.growingcode.global.config.security.JwtProvider;
import bw.growingcode.global.service.GeminiService;
import bw.growingcode.global.service.YoutubeService;
import bw.growingcode.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final GeminiService geminiService;
    private final YoutubeService youtubeService;

    @Operation(summary = "카카오 로그인 redirect uri",
        description = "카카오 로그인 후 인증 결과를 받아 처리하기 위한 uri로 프론트에서 호출 x")
    @GetMapping("/login/oauth2/code/kakao")
    public ResponseEntity<Void> kakaoLoginCallback(@RequestParam String code) {
        //onAuthenticationSuccess 에서 처리
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/test")
//    public ResponseEntity<String> test(@RequestParam String str) {
//        String result = geminiService.getResult(GeminiType.질문, str, null, null);
//        return ResponseEntity.ok(result);
//    }
//
//    @GetMapping("/youtube")
//    public ResponseEntity<String> youtube(@RequestParam String str) {
//        List<String> videoIds = youtubeService.getVideoIds(str);
//        for (String a : videoIds) {
//            System.out.println(a);
//        }
//        return ResponseEntity.ok("");
//    }
//
//    @GetMapping("/review")
//    public ResponseEntity<String> review(@RequestParam String str) {
//        String result = geminiService.getResult(GeminiType.리뷰, str, null, null);
//        return ResponseEntity.ok(result);
//    }
}

