package com.projects.talentflow.agency.service;

import com.projects.talentflow.agency.dto.AgencyResponse;
import com.projects.talentflow.agency.dto.CreateAgencyRequest;
import com.projects.talentflow.agency.entity.Agency;
import com.projects.talentflow.common.service.BaseService;

import java.util.List;
import java.util.UUID;

public interface AgencyService extends BaseService<Agency> {

    AgencyResponse createAgency(CreateAgencyRequest request);

    AgencyResponse getAgencyById(UUID id);

    List<AgencyResponse> getAllAgencies();
}