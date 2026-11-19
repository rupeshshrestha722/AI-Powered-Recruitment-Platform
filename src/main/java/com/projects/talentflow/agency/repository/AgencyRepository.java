package com.projects.talentflow.agency.repository;

import com.projects.talentflow.agency.entity.Agency;
import com.projects.talentflow.common.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AgencyRepository extends BaseRepository<Agency> {

    Optional<Agency> findByEmail(String email);

    boolean existsByEmail(String email);
}
