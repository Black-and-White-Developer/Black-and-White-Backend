package bw.growingcode.code.service;

import bw.growingcode.code.dto.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    private final RestTemplate restTemplate;

    @Autowired
    public CommentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CommentDTO generateComments(String code) {
        CommentDTO commentDTO = new CommentDTO();

        // 클래스별 주석 생성
        commentDTO.setClassComments(generateClassComments(code));

        // 함수별 주석 생성
        commentDTO.setFunctionComments(generateFunctionComments(code));

        // 줄별 주석 생성
        commentDTO.setLineComments(generateLineComments(code));

        return commentDTO;
    }

    private List<String> generateClassComments(String code) {
        List<String> classComments = new ArrayList<>();
        // 여기에 Gemini API를 호출하여 클래스 주석을 생성하는 로직을 추가
        // classComments.add("Class Comment 1");
        return classComments;
    }

    private List<String> generateFunctionComments(String code) {
        List<String> functionComments = new ArrayList<>();
        // 여기에 Gemini API를 호출하여 함수 주석을 생성하는 로직을 추가
        // functionComments.add("Function Comment 1");
        return functionComments;
    }

    private List<String> generateLineComments(String code) {
        List<String> lineComments = new ArrayList<>();
        // 여기에 Gemini API를 호출하여 줄 주석을 생성하는 로직을 추가
        // lineComments.add("Line Comment 1");
        return lineComments;
    }
}
