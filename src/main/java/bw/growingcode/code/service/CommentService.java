package bw.growingcode.code.service;

import bw.growingcode.code.domain.Keyword;
import bw.growingcode.code.domain.Review;
import bw.growingcode.code.dto.CommentDTO;
import bw.growingcode.code.dto.RequestGenerateCommentDTO;
import bw.growingcode.code.enums.GeminiType;
import bw.growingcode.code.repository.KeywordRepository;
import bw.growingcode.code.repository.ReviewRepository;
import bw.growingcode.global.config.utils.Utils;
import bw.growingcode.global.enums.AdditionalType;
import bw.growingcode.global.enums.QuestionType;
import bw.growingcode.global.service.GeminiService;
import bw.growingcode.user.domain.User;
import bw.growingcode.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final GeminiService geminiService;
    private final KeywordRepository keywordRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Transactional
    public CompletableFuture<CommentDTO> generateComments(RequestGenerateCommentDTO requestDto, Long userId) {
        // User 조회
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("user not found"));

        // AdditionalType 설정
        AdditionalType additionalType;
        if (requestDto.additionalType() == 1) {
            additionalType = AdditionalType.클래스;
        } else if (requestDto.additionalType() == 2) {
            additionalType = AdditionalType.함수;
        } else {
            additionalType = AdditionalType.줄;
        }

        // 비동기 작업 시작
        CompletableFuture<String> resultFuture = geminiService.getResultAsync(GeminiType.질문, requestDto.code(), QuestionType.주석, additionalType);
        CompletableFuture<String> keywordFuture = geminiService.getResultAsync(GeminiType.키워드, requestDto.code(), null, null);
        CompletableFuture<String> reviewFuture = geminiService.getResultAsync(GeminiType.리뷰, requestDto.code(), null, null);
//        if (requestDto.isRecord()) {
//            reviewFuture = geminiService.getResultAsync(GeminiType.리뷰, requestDto.code(), null, null);
//        } else {
//            // reviewFuture가 필요 없을 경우 completedFuture(null)로 처리
//            reviewFuture = CompletableFuture.completedFuture(null);
//        }

        // 모든 비동기 작업이 완료될 때까지 대기
        return CompletableFuture.allOf(resultFuture, keywordFuture, reviewFuture)
            .thenApply(voidResult -> {
                try {
                    // 각 작업의 결과 가져오기
                    String result = resultFuture.join();
                    String keywordResult = keywordFuture.join();
                    String reviewResult = reviewFuture.join();

                    // 키워드 저장 (동기적으로 처리)
                    keywordRepository.save(new Keyword(user, keywordResult));

                    // 요약 저장 (동기적으로 처리)
                    reviewRepository.save(new Review(user, requestDto.title(), reviewResult));

                    // CommentDTO 반환
                    return new CommentDTO(result);
                } catch (Exception e) {
                    e.printStackTrace();
                    // 예외 처리: null 반환하거나 적절한 처리
                    return null;
                }
            });
    }
}