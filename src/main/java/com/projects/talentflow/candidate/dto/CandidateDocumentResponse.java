package com.projects.talentflow.candidate.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CandidateDocumentResponse(
        UUID id,
        UUID agencyId,
        UUID candidateId,
        String originalFilename,
        String contentType,
        Long fileSize,
        String parsingStatus,
        UUID uploadedBy,
        String uploadedByName,
        LocalDateTime uploadedAt
) {
}