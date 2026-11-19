package com.projects.talentflow.candidate.repository;

import com.projects.talentflow.candidate.entity.Candidate;
import com.projects.talentflow.common.repository.BaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends BaseRepository<Candidate> {

    List<Candidate> findAllByAgencyId(UUID agencyId);

    Optional<Candidate> findByIdAndAgencyId(UUID id, UUID agencyId);

    boolean existsByIdAndAgencyId(UUID id, UUID agencyId);

    boolean existsByAgencyIdAndEmail(UUID agencyId, String email);

    long countByAgencyId(UUID agencyId);
}