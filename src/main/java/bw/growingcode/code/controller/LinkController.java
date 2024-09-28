package bw.growingcode.code.controller;

import bw.growingcode.code.dto.LinkDTO;
import bw.growingcode.code.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/links")
public class LinkController {

    private final LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/generate")
    public ResponseEntity<LinkDTO> generateLinks(@RequestBody String code) {
        LinkDTO links = linkService.generateLinks(code);
        return ResponseEntity.ok(links);
    }
}
