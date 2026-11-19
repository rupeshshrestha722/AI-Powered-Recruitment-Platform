
package com.projects.talentflow.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ApplicationStatusHistoryResponse(
        UUID id,
        UUID applicationId,
        String previousStatus,
        String newStatus,
        UUID changedBy,
        String changedByName,
        String notes,
        LocalDateTime changedAt
) {
}
