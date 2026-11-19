package com.projects.talentflow.client.repository;

import com.projects.talentflow.client.entity.Client;
import com.projects.talentflow.common.repository.BaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends BaseRepository<Client> {

    List<Client> findAllByAgencyId(UUID agencyId);

    Optional<Client> findByIdAndAgencyId(UUID id, UUID agencyId);

    boolean existsByIdAndAgencyId(UUID id, UUID agencyId);

    boolean existsByAgencyIdAndCompanyNameIgnoreCase(
            UUID agencyId,
            String companyName
    );

    long countByAgencyId(UUID agencyId);
}