package bw.growingcode.code.controller;

import bw.growingcode.code.dto.CommentDTO;
import bw.growingcode.code.dto.RequestGenerateCommentDTO;
import bw.growingcode.code.service.CommentService;
import bw.growingcode.global.config.security.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final JwtProvider jwtProvider;

    @PostMapping
    public ResponseEntity<CommentDTO> generateComments(
        HttpServletRequest httpServletRequest,
        @RequestBody RequestGenerateCommentDTO requestGenerateCommentDto
    ) {
        Long userId = jwtProvider.getUserId(httpServletRequest);

        try {
            // 비동기 작업 수행 및 결과 대기
            CompletableFuture<CommentDTO> commentDtoFuture = commentService.generateComments(requestGenerateCommentDto, userId);
            CommentDTO commentDto = commentDtoFuture.join(); // 작업이 완료될 때까지 대기

            // 작업이 완료되면 결과 반환
            return ResponseEntity.ok(commentDto);

        } catch (Exception ex) {
            // 예외 처리
            System.out.println("An error occurred while generating CommentDTO: " + ex.getMessage());
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}