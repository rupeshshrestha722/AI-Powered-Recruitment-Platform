package com.projects.talentflow.agency.controller;

import com.projects.talentflow.agency.dto.AgencyResponse;
import com.projects.talentflow.agency.dto.CreateAgencyRequest;
import com.projects.talentflow.agency.service.AgencyService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/agencies")
public class AgencyController {

    private final AgencyService agencyService;

    public AgencyController(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<AgencyResponse>> getAllAgencies() {

        return ResponseEntity.ok(agencyService.getAllAgencies());
    }

    @PostMapping
    public ResponseEntity<AgencyResponse> createAgency(
            @Valid @RequestBody CreateAgencyRequest request) {

        AgencyResponse response = agencyService.createAgency(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgencyResponse> getAgencyById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(agencyService.getAgencyById(id));
    }
}