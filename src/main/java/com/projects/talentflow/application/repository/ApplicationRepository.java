
package com.projects.talentflow.application.repository;

import com.projects.talentflow.application.entity.Application;
import com.projects.talentflow.common.repository.BaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApplicationRepository extends BaseRepository<Application> {

    List<Application> findAllByAgencyId(UUID agencyId);

    Optional<Application> findByIdAndAgencyId(UUID id, UUID agencyId);

    boolean existsByIdAndAgencyId(UUID id, UUID agencyId);

    boolean existsByAgencyIdAndCandidateIdAndJobId(
            UUID agencyId,
            UUID candidateId,
            UUID jobId
    );

    List<Application> findAllByAgencyIdAndStatus(
            UUID agencyId,
            String status
    );

    List<Application> findAllByAgencyIdAndCandidateId(
            UUID agencyId,
            UUID candidateId
    );

    List<Application> findAllByAgencyIdAndJobId(
            UUID agencyId,
            UUID jobId
    );

    long countByAgencyId(UUID agencyId);
}
