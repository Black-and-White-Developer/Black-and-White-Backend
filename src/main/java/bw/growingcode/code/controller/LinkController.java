package bw.growingcode.code.controller;

import bw.growingcode.code.dto.LinkDTO;
import bw.growingcode.code.service.LinkService;
import bw.growingcode.global.config.security.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;
    private final JwtProvider jwtProvider;

    @PostMapping
    public ResponseEntity<LinkDTO> generateLinks(
        HttpServletRequest httpServletRequest
    ) {
        Long userId = jwtProvider.getUserId(httpServletRequest);
        LinkDTO links = linkService.generateLinks(userId);
        return ResponseEntity.ok(links);
    }
}
