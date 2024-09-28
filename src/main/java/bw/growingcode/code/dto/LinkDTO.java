package bw.growingcode.code.dto;

import java.util.ArrayList;
import java.util.List;

public class LinkDTO {
    private List<String> links; // 관련 유튜브 링크 리스트

    public LinkDTO() {
        this.links = new ArrayList<>();
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }
}
