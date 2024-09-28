package bw.growingcode.code.controller;

import bw.growingcode.code.dto.MyPageDTO;
import bw.growingcode.code.service.MyPageService;
import bw.growingcode.global.config.security.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final JwtProvider jwtProvider;

    @GetMapping
    public ResponseEntity<MyPageDTO> getMyPage(
        HttpServletRequest httpServletRequest
    ) {
        Long userId = jwtProvider.getUserId(httpServletRequest);
        return ResponseEntity.ok(myPageService.getMyPage(userId));
    }

}