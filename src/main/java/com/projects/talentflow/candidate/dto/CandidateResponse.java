package com.projects.talentflow.candidate.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CandidateResponse(
        UUID id,
        UUID agencyId,
        String firstName,
        String lastName,
        String email,
        String phone,
        String location,
        String currentDesignation,
        String currentCompany,
        BigDecimal totalExperience,
        String noticePeriod,
        String linkedinUrl,
        String summary,
        String status,
        LocalDateTime createdAt
) {
}