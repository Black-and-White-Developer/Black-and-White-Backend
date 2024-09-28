package bw.growingcode.code.service;

import bw.growingcode.code.dto.MyPageDTO;
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
                    it.getTitle(), it.getCode(), it.getContent()
                )
            ).toList()
        );
    }
}
