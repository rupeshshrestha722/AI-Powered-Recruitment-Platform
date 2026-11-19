package com.projects.talentflow.candidate.repository;

import com.projects.talentflow.candidate.entity.CandidateDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CandidateDocumentRepository
        extends JpaRepository<CandidateDocument, UUID> {

    List<CandidateDocument> findAllByCandidateIdAndAgencyIdOrderByUploadedAtDesc(
            UUID candidateId,
            UUID agencyId
    );

    Optional<CandidateDocument> findByIdAndAgencyId(
            UUID id,
            UUID agencyId
    );

    boolean existsByIdAndAgencyId(UUID id, UUID agencyId);

    long countByAgencyId(UUID agencyId);
}