package com.projects.talentflow.job.controller;

import com.projects.talentflow.job.dto.CreateJobRequest;
import com.projects.talentflow.job.dto.JobResponse;
import com.projects.talentflow.job.service.JobService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<JobResponse> createJob(
            @Valid @RequestBody CreateJobRequest request) {

        JobResponse response = jobService.createJob(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<List<JobResponse>> getAllJobs() {

        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<JobResponse> getJobById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<List<JobResponse>> getJobsByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(jobService.getJobsByStatus(status));
    }

    @GetMapping("/client/{clientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'RECRUITER')")
    public ResponseEntity<List<JobResponse>> getJobsByClient(
            @PathVariable UUID clientId) {

        return ResponseEntity.ok(jobService.getJobsByClient(clientId));
    }
}