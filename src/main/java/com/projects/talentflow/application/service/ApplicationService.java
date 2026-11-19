
package com.projects.talentflow.application.service;

import com.projects.talentflow.application.dto.ApplicationResponse;
import com.projects.talentflow.application.dto.ApplicationStatusHistoryResponse;
import com.projects.talentflow.application.dto.CreateApplicationRequest;
import com.projects.talentflow.application.dto.UpdateApplicationStatusRequest;
import com.projects.talentflow.application.entity.Application;
import com.projects.talentflow.common.service.BaseService;

import java.util.List;
import java.util.UUID;

public interface ApplicationService extends BaseService<Application> {

    ApplicationResponse createApplication(CreateApplicationRequest request);

    List<ApplicationResponse> getAllApplications();

    ApplicationResponse getApplicationById(UUID applicationId);

    List<ApplicationResponse> getApplicationsByStatus(String status);

    List<ApplicationResponse> getApplicationsByCandidate(UUID candidateId);

    List<ApplicationResponse> getApplicationsByJob(UUID jobId);

    ApplicationResponse updateApplicationStatus(
            UUID applicationId,
            UpdateApplicationStatusRequest request
    );

    List<ApplicationStatusHistoryResponse> getApplicationStatusHistory(
            UUID applicationId
    );
}
