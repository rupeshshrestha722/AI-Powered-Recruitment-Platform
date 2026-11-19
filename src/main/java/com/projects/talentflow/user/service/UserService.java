package com.projects.talentflow.user.service;

import com.projects.talentflow.common.service.BaseService;
import com.projects.talentflow.user.dto.CreateUserRequest;
import com.projects.talentflow.user.dto.UserResponse;
import com.projects.talentflow.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService extends BaseService<User> {

    Optional<User> findByAgencyIdAndEmail(UUID agencyId, String email);

    boolean existByAgencyIdAndEmail(UUID agencyId, String email);

    UserResponse createUser(CreateUserRequest request, UUID agencyId);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(UUID userId);
}
