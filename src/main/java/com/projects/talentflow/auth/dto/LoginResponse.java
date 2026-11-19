package com.projects.talentflow.auth.dto;


import java.util.UUID;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        UUID userId,
        UUID agencyId,
        String email,
        String role
) {
}
