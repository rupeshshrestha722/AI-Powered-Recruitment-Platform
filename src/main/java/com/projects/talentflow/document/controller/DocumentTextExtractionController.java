
package com.projects.talentflow.document.controller;

import com.projects.talentflow.document.service.DocumentTextExtractionService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/candidate-documents")
public class DocumentTextExtractionController {

    private final DocumentTextExtractionService extractionService;

    public DocumentTextExtractionController(
            DocumentTextExtractionService extractionService
    ) {
        this.extractionService = extractionService;
    }

    @PostMapping("/{documentId}/extract-text")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<Map<String, String>> extractText(
            @PathVariable UUID documentId
    ) {

        String extractedText =
                extractionService.extractCandidateDocumentText(documentId);

        return ResponseEntity.ok(
                Map.of(
                        "documentId", documentId.toString(),
                        "extractedText", extractedText
                )
        );
    }
}
