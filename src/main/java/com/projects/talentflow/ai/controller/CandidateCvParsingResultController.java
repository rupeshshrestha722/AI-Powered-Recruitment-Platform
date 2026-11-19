
package com.projects.talentflow.ai.controller;

import com.projects.talentflow.ai.dto.CvParsingReviewRequest;
import com.projects.talentflow.ai.dto.CvParsingReviewResponse;
import com.projects.talentflow.ai.entity.CandidateCvParsingResult;
import com.projects.talentflow.ai.service.CandidateCvParsingResultService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ai/cv/results")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
public class CandidateCvParsingResultController {

    private final CandidateCvParsingResultService resultService;

    public CandidateCvParsingResultController(
            CandidateCvParsingResultService resultService
    ) {
        this.resultService = resultService;
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<CandidateCvParsingResult> getResult(
            @PathVariable UUID documentId
    ) {
        return ResponseEntity.ok(
                resultService.getParsingResult(documentId)
        );
    }

    @GetMapping
    public ResponseEntity<List<CandidateCvParsingResult>> getAllResults() {
        return ResponseEntity.ok(
                resultService.getAllParsingResults()
        );
    }

    @GetMapping("/{documentId}/review")
    public ResponseEntity<CvParsingReviewResponse> getReviewResult(
            @PathVariable UUID documentId
    ) {
        return ResponseEntity.ok(
                resultService.getReviewResult(documentId)
        );
    }


    @PutMapping("/{documentId}/review")
    public ResponseEntity<CvParsingReviewResponse> updateReview(
            @PathVariable UUID documentId,
            @Valid
            @RequestBody CvParsingReviewRequest request
    ) {
        return ResponseEntity.ok(
                resultService.updateReview(documentId, request)
        );
    }

}
