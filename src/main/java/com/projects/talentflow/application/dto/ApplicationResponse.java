
package com.projects.talentflow.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ApplicationResponse(
        UUID id,
        UUID agencyId,
        UUID candidateId,
        String candidateName,
        UUID jobId,
        String jobTitle,
        String clientName,
        String status,
        String source,
        String recruiterNotes,
        LocalDateTime appliedAt,
        LocalDateTime createdAt
) {
}
