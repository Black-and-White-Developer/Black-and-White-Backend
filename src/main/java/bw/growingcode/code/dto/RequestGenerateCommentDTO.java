package bw.growingcode.code.dto;

public record RequestGenerateCommentDTO(
    String code,
    int additionalType,
    boolean isRecord
) {
}
