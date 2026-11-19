
package com.projects.talentflow.candidate.controller;

import com.projects.talentflow.candidate.dto.CandidateDocumentResponse;
import com.projects.talentflow.candidate.service.CandidateDocumentService;

import jakarta.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CandidateDocumentController {

    private final CandidateDocumentService documentService;

    public CandidateDocumentController(
            CandidateDocumentService documentService
    ) {
        this.documentService = documentService;
    }

    @PostMapping(
            value = "/candidates/{candidateId}/documents",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<CandidateDocumentResponse> uploadDocument(
            @PathVariable UUID candidateId,
            @RequestPart("file") @NotNull MultipartFile file
    ) {
        CandidateDocumentResponse response =
                documentService.uploadCandidateDocument(candidateId, file);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/candidates/{candidateId}/documents")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<List<CandidateDocumentResponse>> getCandidateDocuments(
            @PathVariable UUID candidateId
    ) {
        return ResponseEntity.ok(
                documentService.getCandidateDocuments(candidateId)
        );
    }

    @GetMapping("/candidate-documents/{documentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<CandidateDocumentResponse> getDocumentById(
            @PathVariable UUID documentId
    ) {
        return ResponseEntity.ok(
                documentService.getDocumentById(documentId)
        );
    }

    @DeleteMapping("/candidate-documents/{documentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> deleteDocument(
            @PathVariable UUID documentId
    ) {
        documentService.deleteDocument(documentId);

        return ResponseEntity.noContent().build();
    }
}
