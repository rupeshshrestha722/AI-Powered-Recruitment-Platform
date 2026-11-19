package com.projects.talentflow.user.dto;

import com.projects.talentflow.user.entity.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        UUID agencyId,
        String firstName,
        String lastName,
        String email,
        UserRole role,
        String status,
        LocalDateTime createdAt
) {
}