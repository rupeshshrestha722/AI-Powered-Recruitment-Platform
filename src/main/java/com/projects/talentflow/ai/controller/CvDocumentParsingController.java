
package com.projects.talentflow.ai.controller;

import com.projects.talentflow.ai.dto.CvParsingResponse;
import com.projects.talentflow.ai.service.CvDocumentParsingService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/ai/cv")
public class CvDocumentParsingController {

    private final CvDocumentParsingService parsingService;

    public CvDocumentParsingController(
            CvDocumentParsingService parsingService
    ) {
        this.parsingService = parsingService;
    }

    @PostMapping("/{documentId}/parse")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<CvParsingResponse> parseCv(
            @PathVariable UUID documentId
    ) {
        CvParsingResponse response =
                parsingService.parseDocument(documentId);

        return ResponseEntity.ok(response);
    }
}
