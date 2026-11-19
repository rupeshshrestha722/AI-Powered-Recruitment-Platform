package com.projects.talentflow.job.repository;

import com.projects.talentflow.common.repository.BaseRepository;
import com.projects.talentflow.job.entity.Job;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobRepository extends BaseRepository<Job> {

    List<Job> findAllByAgencyId(UUID agencyId);

    Optional<Job> findByIdAndAgencyId(UUID id, UUID agencyId);

    boolean existsByIdAndAgencyId(UUID id, UUID agencyId);

    List<Job> findAllByAgencyIdAndStatus(UUID agencyId, String status);

    List<Job> findAllByAgencyIdAndClientId(UUID agencyId, UUID clientId);

    long countByAgencyId(UUID agencyId);
}