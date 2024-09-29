package bw.growingcode.code.dto;

import java.util.List;
public record MyPageDTO(
    List<ReviewDTO> reviews
) {
    public record ReviewDTO(
        String title,
        String content
    ) {
    }
}
