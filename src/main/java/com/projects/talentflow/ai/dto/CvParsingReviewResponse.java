
package com.projects.talentflow.ai.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CvParsingReviewResponse(
        UUID id,
        UUID candidateDocumentId,
        String parsingStatus,
        String reviewStatus,
        String reviewedBy,
        LocalDateTime reviewedAt,
        CvParsingResponse parsedData
) {
}
