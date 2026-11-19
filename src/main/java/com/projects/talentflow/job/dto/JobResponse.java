package com.projects.talentflow.job.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record JobResponse(
        UUID id,
        UUID agencyId,
        UUID clientId,
        String clientName,
        String title,
        String description,
        String requiredSkills,
        BigDecimal minExperience,
        BigDecimal maxExperience,
        String employmentType,
        String workplaceType,
        String country,
        String city,
        BigDecimal minSalary,
        BigDecimal maxSalary,
        String salaryCurrency,
        Integer openings,
        String status,
        LocalDate closingDate,
        LocalDateTime createdAt
) {
}