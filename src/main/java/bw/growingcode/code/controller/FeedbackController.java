package bw.growingcode.code.controller;

import bw.growingcode.code.dto.FeedbackDTO;
import bw.growingcode.code.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/generate")
    public ResponseEntity<FeedbackDTO> generateFeedback(@RequestBody String code) {
        FeedbackDTO feedback = feedbackService.generateFeedback(code);
        return ResponseEntity.ok(feedback);
    }
}
