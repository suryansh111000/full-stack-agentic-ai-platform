package com.agentic.ai.controller;

import com.agentic.ai.dto.AIRequest;
import com.agentic.ai.dto.AIResponse;
import com.agentic.ai.service.AIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/process")
    public ResponseEntity<AIResponse> process(@RequestBody AIRequest request) {
        return ResponseEntity.ok(aiService.process(request));
    }
}