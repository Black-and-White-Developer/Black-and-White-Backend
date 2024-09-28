package bw.growingcode.global.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class YoutubeService {

    private final RestTemplate restTemplate;
    @Value("${youtube.data-api.key}")
    private String key;

    public List<String> getVideoIds(String keyword) {
        String url = "https://www.googleapis.com/youtube/v3/search?type=video&regionCode=KR&maxResults=20";
        url += "&key=" + key;
        url += "&q=" + keyword;
        String jsonString = restTemplate.getForEntity(url, String.class).getBody();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // JSON 문자열을 JsonNode로 변환
            JsonNode rootNode = objectMapper.readTree(jsonString);

            JsonNode items = rootNode.path("items");

            List<String> videoIds = new ArrayList<>();

            // items 배열을 순회하며 각 item의 "id" -> "videoId"를 리스트에 추가
            for (JsonNode item : items) {
                JsonNode idNode = item.path("id");
                String videoId = idNode.path("videoId").asText();
                if (!videoId.isEmpty()) {
                    videoIds.add("https://www.youtube.com/embed/" + videoId);
                }
            }

            return videoIds;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}