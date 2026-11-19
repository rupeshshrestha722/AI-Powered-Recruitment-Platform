package com.projects.talentflow.application.repository;

import com.projects.talentflow.application.entity.ApplicationStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApplicationStatusHistoryRepository
        extends JpaRepository<ApplicationStatusHistory, UUID> {

    List<ApplicationStatusHistory>
    findAllByApplicationIdAndAgencyIdOrderByChangedAtAsc(
            UUID applicationId,
            UUID agencyId
    );

    List<ApplicationStatusHistory> findAllByAgencyIdOrderByChangedAtDesc(
            UUID agencyId
    );

    long countByAgencyId(UUID agencyId);
}