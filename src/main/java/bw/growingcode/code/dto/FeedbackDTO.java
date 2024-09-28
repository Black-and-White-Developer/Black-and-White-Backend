package bw.growingcode.code.dto;

public record FeedbackDTO(
    String readabilityResult, // 가독성 코드
    String performanceResult // 성능 코드
) {
}
