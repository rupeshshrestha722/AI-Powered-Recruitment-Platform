package com.projects.talentflow.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.UUID;

public final class TenantContext {

    private TenantContext() {

    }

    public static UUID getAgencyId() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) {
            throw new IllegalStateException(
                    "Authenticated JWT user not found"
            );
        }

        String agencyId = jwtAuth.getToken().getClaimAsString("agencyId");

        if (agencyId == null || agencyId.isBlank()) throw new IllegalStateException("Agency ID not found in JWT");

        return UUID.fromString(agencyId);
    }

}
