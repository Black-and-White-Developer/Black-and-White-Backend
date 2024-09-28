package bw.growingcode.code.dto;

public record RequestGenerateCommentDTO(
    String title,
    String code,
    int additionalType
) {
}
