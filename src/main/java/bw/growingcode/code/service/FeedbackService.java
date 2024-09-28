package bw.growingcode.code.service;

import bw.growingcode.code.dto.FeedbackDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class FeedbackService {

    private final RestTemplate restTemplate;
    private final String apiKey = "YOUR_API_KEY"; // 여기에 API 키를 추가하세요
    private final String geminiApiUrl = "https://api.gemini.com/your-endpoint"; // 실제 Gemini API 엔드포인트

    @Autowired
    public FeedbackService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public FeedbackDTO generateFeedback(String code) {
        // 요청할 데이터 구조를 정의
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("code", code);
        requestBody.put("api_key", apiKey);

        // Gemini API 호출
        FeedbackResponse response = restTemplate.postForObject(geminiApiUrl, requestBody, FeedbackResponse.class);

        // 결과를 FeedbackDTO로 매핑
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        if (response != null) {
            feedbackDTO.setReadabilityImprovement(response.getReadabilityImprovement()); // 가독성 향상 코드
            feedbackDTO.setPerformanceImprovement(response.getPerformanceImprovement()); // 성능 향상 코드
        }

        return feedbackDTO;
    }
}
