package com.projects.talentflow.user.repository;

import com.projects.talentflow.common.repository.BaseRepository;
import com.projects.talentflow.user.entity.User;
import com.projects.talentflow.user.entity.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByAgencyIdAndEmail(UUID agencyId, String email);

    boolean existsByAgencyIdAndEmail(UUID agencyId, String email);

    List<User> findAllByAgencyId(UUID agencyId);

    Optional<User> findByIdAndAgencyId(UUID id, UUID agencyId);

    boolean existsByIdAndAgencyId(UUID id, UUID agencyId);

    long countByAgencyId(UUID agencyId);

    Optional<User> findByRoleAndEmailIgnoreCase(UserRole role, String email);

    boolean existsByRoleAndEmailIgnoreCase(UserRole role, String email);
}
