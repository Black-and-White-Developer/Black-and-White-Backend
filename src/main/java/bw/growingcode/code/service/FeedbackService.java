package bw.growingcode.code.service;

import bw.growingcode.code.domain.Keyword;
import bw.growingcode.code.domain.Review;
import bw.growingcode.code.dto.FeedbackDTO;
import bw.growingcode.code.dto.RequestGenerateFeedbackDTO;
import bw.growingcode.code.enums.GeminiType;
import bw.growingcode.code.repository.KeywordRepository;
import bw.growingcode.code.repository.ReviewRepository;
import bw.growingcode.global.config.utils.Utils;
import bw.growingcode.global.enums.QuestionType;
import bw.growingcode.global.service.GeminiService;
import bw.growingcode.user.domain.User;
import bw.growingcode.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    
    private final GeminiService geminiService;
    private final KeywordRepository keywordRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Transactional
    public CompletableFuture<FeedbackDTO> generateFeedback(RequestGenerateFeedbackDTO requestDto, Long userId) {
        // User 조회
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("user not found"));

        // 비동기 작업 시작 (동시에 실행되도록 설정)
        CompletableFuture<String> readabilityFuture = geminiService.getResultAsync(GeminiType.질문, requestDto.code(), QuestionType.가독성, null);
        CompletableFuture<String> performanceFuture = geminiService.getResultAsync(GeminiType.질문, requestDto.code(), QuestionType.성능, null);
        CompletableFuture<String> keywordFuture = geminiService.getResultAsync(GeminiType.키워드, requestDto.code(), null, null);
        CompletableFuture<String> reviewFuture = geminiService.getResultAsync(GeminiType.리뷰, requestDto.code(), null, null);

        // 모든 비동기 작업이 완료될 때까지 대기
        return CompletableFuture.allOf(readabilityFuture, performanceFuture, keywordFuture, reviewFuture)
            .thenApply(voidResult -> {
                try {
                    // 각 작업의 결과 가져오기
                    String readabilityResult = readabilityFuture.join();
                    String performanceResult = performanceFuture.join();
                    String keywordResult = keywordFuture.join();
                    String reviewResult = reviewFuture.join();

                    // 결과를 FeedbackDTO로 매핑
                    FeedbackDTO feedbackDTO = new FeedbackDTO(readabilityResult, performanceResult);

                    // 트랜잭션 내에서 키워드 저장
                    keywordRepository.save(new Keyword(user, keywordResult));

                    // 트랜잭션 내에서 요약 저장
                    reviewRepository.save(new Review(user, requestDto.title(), reviewResult));

                    return feedbackDTO;
                } catch (Exception e) {
                    throw new RuntimeException("Failed to generate feedback", e);  // 예외 처리
                }
            });
    }
}
