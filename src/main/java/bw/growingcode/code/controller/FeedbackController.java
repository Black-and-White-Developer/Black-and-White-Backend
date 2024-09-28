package bw.growingcode.code.controller;

import bw.growingcode.code.dto.CommentDTO;
import bw.growingcode.code.dto.FeedbackDTO;
import bw.growingcode.code.dto.RequestGenerateFeedbackDTO;
import bw.growingcode.code.service.FeedbackService;
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
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final JwtProvider jwtProvider;

    @PostMapping
    public ResponseEntity<FeedbackDTO> generateFeedback(
        HttpServletRequest httpServletRequest,
        @RequestBody RequestGenerateFeedbackDTO requestGenerateFeedbackDTO
    ) {
        Long userId = jwtProvider.getUserId(httpServletRequest);
        try {
            // 비동기 작업 수행 및 결과 대기
            CompletableFuture<FeedbackDTO> commentDtoFuture = feedbackService.generateFeedback(requestGenerateFeedbackDTO, userId);
            FeedbackDTO feedbackDTO = commentDtoFuture.join(); // 작업이 완료될 때까지 대기

            // 작업이 완료되면 결과 반환
            return ResponseEntity.ok(feedbackDTO);

        } catch (Exception ex) {
            // 예외 처리
            System.out.println("An error occurred while generating FeedbackDTO: " + ex.getMessage());
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
