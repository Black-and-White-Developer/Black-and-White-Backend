package bw.growingcode.code.service;

import bw.growingcode.code.domain.Review;
import bw.growingcode.code.dto.MyPageDTO;
import bw.growingcode.code.dto.PostMyPageDTO;
import bw.growingcode.code.repository.ReviewRepository;
import bw.growingcode.user.domain.User;
import bw.growingcode.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public MyPageDTO getMyPage(Long userId) {
        // User 조회
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("user not found"));

        //최신순으로 리뷰 정렬
        return new MyPageDTO(
            reviewRepository.findAllByUserOrderByIdDesc(user).stream().map(
                it -> new MyPageDTO.ReviewDTO(
                    it.getTitle(), it.getContent()
                )
            ).toList()
        );
    }

    @Transactional
    public void postReview(PostMyPageDTO request, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("user not found"));

        reviewRepository.save(
            new Review(
                user, request.content().split(" ")[0], request.content()
            )
        );
    }
}
