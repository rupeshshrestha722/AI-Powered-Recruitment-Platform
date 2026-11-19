package com.projects.talentflow.agency.controller;

import com.projects.talentflow.agency.dto.AgencyResponse;
import com.projects.talentflow.agency.dto.CreateAgencyWithAdminRequest;
import com.projects.talentflow.agency.service.PlatformAgencyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/platform/agencies")
@PreAuthorize("hasRole('PLATFORM_ADMIN')")
public class PlatformAgencyController {

    private final PlatformAgencyService platformAgencyService;

    public PlatformAgencyController(
            PlatformAgencyService platformAgencyService) {
        this.platformAgencyService = platformAgencyService;
    }

    @PostMapping
    public ResponseEntity<AgencyResponse> createAgency(
            @Valid @RequestBody CreateAgencyWithAdminRequest request) {

        AgencyResponse response =
                platformAgencyService.createAgencyWithAdmin(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}