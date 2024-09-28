package bw.growingcode.code.controller;

import bw.growingcode.code.dto.CommentDTO;
import bw.growingcode.code.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/generate")
    public ResponseEntity<CommentDTO> generateComments(@RequestBody String code) {
        CommentDTO comments = commentService.generateComments(code);
        return ResponseEntity.ok(comments);
    }
}