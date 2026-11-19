
package com.projects.talentflow.ai.repository;

import com.projects.talentflow.ai.entity.CandidateCvParsingResult;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CandidateCvParsingResultRepository
        extends JpaRepository<CandidateCvParsingResult, UUID> {

    Optional<CandidateCvParsingResult>
    findByCandidateDocumentIdAndAgencyId(
            UUID candidateDocumentId,
            UUID agencyId
    );

    List<CandidateCvParsingResult> findAllByAgencyId(
            UUID agencyId
    );

    boolean existsByCandidateDocumentIdAndAgencyId(
            UUID candidateDocumentId,
            UUID agencyId
    );
}
