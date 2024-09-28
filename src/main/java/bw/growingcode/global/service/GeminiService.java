package bw.growingcode.global.service;

import bw.growingcode.code.enums.GeminiType;
import bw.growingcode.global.config.utils.Utils;
import bw.growingcode.global.dto.JsonRequest;
import bw.growingcode.global.enums.AdditionalType;
import bw.growingcode.global.enums.QuestionType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class GeminiService {

    private final RestTemplate restTemplate;
    @Value("${google.gemini.key}")
    private String key;

    @Async
    public CompletableFuture<String> getResultAsync(GeminiType geminiType, String questionCode, QuestionType questionType, AdditionalType additionalType) {
        // 외부 요청 처리 (시간이 걸리는 작업)
        String result = getResult(geminiType, questionCode, questionType, additionalType);
        return CompletableFuture.completedFuture(result);
    }

    public String getResult(GeminiType geminiType, String questionCode, QuestionType questionType, AdditionalType additionalType) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent";
        url += "?key=" + key;
        String content;
        if (geminiType == GeminiType.질문) {
            content = createQuestion(questionCode, questionType, additionalType);
        } else if (geminiType == GeminiType.키워드){
            content = createKeywordQuestion(questionCode);
        } else {
            content = createReviewQuestion(questionCode);
        }
        String jsonString = restTemplate.postForEntity(url, content, String.class).getBody();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // JSON 문자열을 JsonNode로 변환
            JsonNode rootNode = objectMapper.readTree(jsonString);

            // "candidates" 배열 가져오기
            JsonNode candidatesNode = rootNode.path("candidates").get(0);

            // "content" 객체 가져오기
            JsonNode contentNode = candidatesNode.path("content");

            // "parts" 배열 가져오기
            JsonNode partsNode = contentNode.path("parts").get(0);

            // "text" 값 가져오기
            String text = partsNode.path("text").asText();

            return text;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String createQuestion(String questionCode, QuestionType questionType, AdditionalType additionalTypes) {
        StringBuilder sb = new StringBuilder();
        String decodedStr = Utils.decodeString(questionCode);
        sb.append(decodedStr).append("\n").append("\n");
        sb.append("기존 코드는 유지한 채로 ");
        switch (questionType) {
            case 주석:
                if (additionalTypes == null) {
                    throw new IllegalArgumentException("주석 질문은 추가 타입을 입력해주세요.");
                }
                switch (additionalTypes) {
                    case 클래스: sb.append("이 클래스가 어떤 역할을 하는 클래스인지 답변 첫번째 줄에 한줄로 해당 언어에 맞는 주석을 달아줘."); break;
                    case 함수: sb.append("각 함수마다 어떤 역할을 담당하는 함수인지 각 함수 위에만 주석을 달아줘."); break;
                    case 줄: sb.append("각 줄마다 어떤 건지 주석을 달아줘."); break;
                }
            case 가독성:
                sb.append("가독성 좋게 코드를 바꿔줘"); break;
            case 성능:
                sb.append("성능 좋게 코드를 바꿔줘"); break;
        }
        return createJsonRequest(sb.toString());
    }

    private String createJsonRequest(String content) {
        // JsonRequest 객체 생성
        JsonRequest.Part part = new JsonRequest.Part();
        part.setText(content);

        JsonRequest.Content contentObj = new JsonRequest.Content();
        contentObj.setParts(Collections.singletonList(part));

        JsonRequest request = new JsonRequest();
        request.setContents(Collections.singletonList(contentObj));

        // ObjectMapper를 사용하여 Java 객체를 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String result = null;
        try {
            result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
        } catch (Exception e) {
        }
        return result;
    }

    private String createKeywordQuestion(String questionCode) {
        StringBuilder sb = new StringBuilder();
        String decodedStr = Utils.decodeString(questionCode);
        sb.append(decodedStr).append("\n").append("\n");
        sb.append("이 코드의 미흡한 점을 인터넷에 검색해서 공부할려고 하는데 뭐라고 검색하면 될까? 한개만 알려줘");
        return createJsonRequest(sb.toString());
    }

    private String createReviewQuestion(String questionCode) {
        StringBuilder sb = new StringBuilder();
        String decodedStr = Utils.decodeString(questionCode);
        sb.append(decodedStr).append("\n").append("\n");
        sb.append("이 코드의 미흡한 점을 최대 10줄로 요약해줘.");
        return createJsonRequest(sb.toString());
    }

}