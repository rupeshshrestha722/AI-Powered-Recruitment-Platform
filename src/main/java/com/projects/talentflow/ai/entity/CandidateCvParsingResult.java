
package com.projects.talentflow.ai.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import tools.jackson.databind.JsonNode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
        name = "candidate_cv_parsing_results",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_cv_parsing_document",
                        columnNames = "candidate_document_id"
                )
        },
        indexes = {
                @Index(
                        name = "idx_cv_parsing_agency",
                        columnList = "agency_id"
                )
        }
)
public class CandidateCvParsingResult {

    @Id
    private UUID id;

    @Column(name = "agency_id", nullable = false)
    private UUID agencyId;

    @Column(name = "candidate_document_id", nullable = false)
    private UUID candidateDocumentId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "parsed_data", columnDefinition = "jsonb", nullable = false)
    private Map<String, Object> parsedData;

    @Column(name = "model_name", length = 100)
    private String modelName;

    @Column(name = "parsing_status", nullable = false, length = 30)
    private String parsingStatus = "COMPLETED";

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "review_status", nullable = false, length = 30)
    private String reviewStatus = "PENDING";

    @Column(name = "reviewed_by")
    private UUID reviewedBy;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();

        if (id == null) {
            id = UUID.randomUUID();
        }

        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public CandidateCvParsingResult() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(UUID agencyId) {
        this.agencyId = agencyId;
    }

    public UUID getCandidateDocumentId() {
        return candidateDocumentId;
    }

    public void setCandidateDocumentId(UUID candidateDocumentId) {
        this.candidateDocumentId = candidateDocumentId;
    }

    public Map<String, Object> getParsedData() {
        return parsedData;
    }

    public void setParsedData(Map<String, Object> parsedData) {
        this.parsedData = parsedData;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getParsingStatus() {
        return parsingStatus;
    }

    public void setParsingStatus(String parsingStatus) {
        this.parsingStatus = parsingStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public UUID getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(UUID reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
}
