package com.projects.talentflow.candidate.controller;

import com.projects.talentflow.candidate.dto.CandidateResponse;
import com.projects.talentflow.candidate.dto.CreateCandidateRequest;
import com.projects.talentflow.candidate.service.CandidateService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<CandidateResponse> createCandidate(
            @Valid @RequestBody CreateCandidateRequest request) {

        CandidateResponse response =
                candidateService.createCandidate(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<List<CandidateResponse>> getAllCandidates() {

        return ResponseEntity.ok(candidateService.getAllCandidates());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<CandidateResponse> getCandidateById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(candidateService.getCandidateById(id));
    }
}