package bw.growingcode.code.service;

import bw.growingcode.code.domain.Keyword;
import bw.growingcode.code.dto.LinkDTO;
import bw.growingcode.code.repository.KeywordRepository;
import bw.growingcode.global.service.YoutubeService;
import bw.growingcode.user.domain.User;
import bw.growingcode.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final KeywordRepository keywordRepository;
    private final UserRepository userRepository;
    private final YoutubeService youtubeService;

    public LinkDTO generateLinks(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("user not found"));

        List<Keyword> keywords = keywordRepository.findAllByUser(user);

        String randomKeyword = keywords.isEmpty() ? "개발자 성장" : getRandomKeyword(keywords).getContent();

        List<String> result = youtubeService.getVideoIds(randomKeyword);

        return new LinkDTO(result);
    }

    private Keyword getRandomKeyword(List<Keyword> keywords) {
        Random random = new Random();
        // 리스트에서 랜덤한 인덱스 선택
        int randomIndex = random.nextInt(keywords.size());
        return keywords.get(randomIndex);
    }
}
