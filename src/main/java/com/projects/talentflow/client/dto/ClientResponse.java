package com.projects.talentflow.client.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClientResponse(
        UUID id,
        UUID agencyId,
        String companyName,
        String industry,
        String email,
        String phone,
        String website,
        String country,
        String city,
        String address,
        String status,
        LocalDateTime createdAt
) {
}