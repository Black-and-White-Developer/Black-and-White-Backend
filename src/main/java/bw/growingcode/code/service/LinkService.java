package bw.growingcode.code.service;

import bw.growingcode.code.dto.LinkDTO;
import org.springframework.stereotype.Service;

@Service
public class LinkService {

    public LinkDTO generateLinks(String code) {
        // Gemini API로부터 링크 정보 가져오기
        LinkDTO linkDTO = new LinkDTO();
        // linkDTO에 링크 정보 추가
        return linkDTO;
    }
}
