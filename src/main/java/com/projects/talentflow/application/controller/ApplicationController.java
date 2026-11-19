
package com.projects.talentflow.application.controller;

import com.projects.talentflow.application.dto.ApplicationResponse;
import com.projects.talentflow.application.dto.ApplicationStatusHistoryResponse;
import com.projects.talentflow.application.dto.CreateApplicationRequest;
import com.projects.talentflow.application.dto.UpdateApplicationStatusRequest;
import com.projects.talentflow.application.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<ApplicationResponse> createApplication(
            @Valid @RequestBody CreateApplicationRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(applicationService.createApplication(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<List<ApplicationResponse>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<ApplicationResponse> getApplicationById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsByStatus(
            @PathVariable String status
    ) {
        return ResponseEntity.ok(
                applicationService.getApplicationsByStatus(status)
        );
    }

    @GetMapping("/candidate/{candidateId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsByCandidate(
            @PathVariable UUID candidateId
    ) {
        return ResponseEntity.ok(
                applicationService.getApplicationsByCandidate(candidateId)
        );
    }

    @GetMapping("/job/{jobId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsByJob(
            @PathVariable UUID jobId
    ) {
        return ResponseEntity.ok(
                applicationService.getApplicationsByJob(jobId)
        );
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<ApplicationResponse> updateApplicationStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateApplicationStatusRequest request
    ) {
        return ResponseEntity.ok(
                applicationService.updateApplicationStatus(id, request)
        );
    }

    @GetMapping("/{id}/history")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<List<ApplicationStatusHistoryResponse>>
    getApplicationStatusHistory(@PathVariable UUID id) {

        return ResponseEntity.ok(
                applicationService.getApplicationStatusHistory(id)
        );
    }
}
