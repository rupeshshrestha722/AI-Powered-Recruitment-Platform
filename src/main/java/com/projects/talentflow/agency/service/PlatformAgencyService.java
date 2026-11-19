package com.projects.talentflow.agency.service;

import com.projects.talentflow.agency.dto.AgencyResponse;
import com.projects.talentflow.agency.dto.CreateAgencyWithAdminRequest;

public interface PlatformAgencyService {

    AgencyResponse createAgencyWithAdmin(
            CreateAgencyWithAdminRequest request
    );
}
