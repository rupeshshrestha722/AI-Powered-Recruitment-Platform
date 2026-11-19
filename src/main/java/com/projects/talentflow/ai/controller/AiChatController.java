
package com.projects.talentflow.ai.controller;

import com.projects.talentflow.ai.service.AiChatService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiChatController {

    private final AiChatService aiChatService;

    public AiChatController(AiChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    @PostMapping("/chat")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<Map<String, String>> chat(
            @RequestBody Map<String, String> request
    ) {

        String prompt = request.get("prompt");

        if (prompt == null || prompt.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Prompt is required"));
        }

        String response = aiChatService.generateResponse(prompt);

        return ResponseEntity.ok(
                Map.of("response", response)
        );
    }
}
