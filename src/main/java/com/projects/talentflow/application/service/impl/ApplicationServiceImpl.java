
package com.projects.talentflow.application.service.impl;

import com.projects.talentflow.application.dto.ApplicationResponse;
import com.projects.talentflow.application.dto.ApplicationStatusHistoryResponse;
import com.projects.talentflow.application.dto.CreateApplicationRequest;
import com.projects.talentflow.application.dto.UpdateApplicationStatusRequest;
import com.projects.talentflow.application.entity.Application;
import com.projects.talentflow.application.entity.ApplicationStatus;
import com.projects.talentflow.application.entity.ApplicationStatusHistory;
import com.projects.talentflow.application.entity.ApplicationStatusTransition;
import com.projects.talentflow.application.repository.ApplicationRepository;
import com.projects.talentflow.application.repository.ApplicationStatusHistoryRepository;
import com.projects.talentflow.application.service.ApplicationService;
import com.projects.talentflow.candidate.entity.Candidate;
import com.projects.talentflow.candidate.repository.CandidateRepository;
import com.projects.talentflow.common.service.impl.BaseServiceImpl;
import com.projects.talentflow.job.entity.Job;
import com.projects.talentflow.job.repository.JobRepository;
import com.projects.talentflow.security.TenantContext;
import com.projects.talentflow.user.entity.User;
import com.projects.talentflow.user.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ApplicationServiceImpl
        extends BaseServiceImpl<Application>
        implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;
    private final ApplicationStatusHistoryRepository applicationStatusHistoryRepository;
    private final UserRepository userRepository;

    public ApplicationServiceImpl(
            ApplicationRepository applicationRepository,
            CandidateRepository candidateRepository,
            JobRepository jobRepository,
            ApplicationStatusHistoryRepository applicationStatusHistoryRepository,
            UserRepository userRepository
    ) {
        super(applicationRepository);
        this.applicationRepository = applicationRepository;
        this.candidateRepository = candidateRepository;
        this.jobRepository = jobRepository;
        this.applicationStatusHistoryRepository = applicationStatusHistoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ApplicationResponse createApplication(CreateApplicationRequest request) {

        UUID agencyId = TenantContext.getAgencyId();

        Candidate candidate = candidateRepository
                .findByIdAndAgencyId(request.getCandidateId(), agencyId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Candidate not found in your agency"
                        )
                );

        Job job = jobRepository
                .findByIdAndAgencyId(request.getJobId(), agencyId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Job not found in your agency"
                        )
                );

        if (applicationRepository.existsByAgencyIdAndCandidateIdAndJobId(
                agencyId,
                candidate.getId(),
                job.getId()
        )) {
            throw new IllegalArgumentException(
                    "This candidate already has an application for this job"
            );
        }

        Application application = new Application();
        application.setAgency(candidate.getAgency());
        application.setCandidate(candidate);
        application.setJob(job);
        application.setStatus("SOURCED");
        application.setSource(request.getSource());
        application.setRecruiterNotes(request.getRecruiterNotes());

        Application saved = applicationRepository.save(application);

        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationResponse> getAllApplications() {

        UUID agencyId = TenantContext.getAgencyId();

        return applicationRepository.findAllByAgencyId(agencyId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationResponse getApplicationById(UUID applicationId) {

        UUID agencyId = TenantContext.getAgencyId();

        Application application = applicationRepository
                .findByIdAndAgencyId(applicationId, agencyId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Application not found")
                );

        return mapToResponse(application);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationResponse> getApplicationsByStatus(String status) {

        UUID agencyId = TenantContext.getAgencyId();

        return applicationRepository
                .findAllByAgencyIdAndStatus(
                        agencyId,
                        status.trim().toUpperCase()
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationResponse> getApplicationsByCandidate(UUID candidateId) {

        UUID agencyId = TenantContext.getAgencyId();

        if (!candidateRepository.existsByIdAndAgencyId(candidateId, agencyId)) {
            throw new IllegalArgumentException("Candidate not found");
        }

        return applicationRepository
                .findAllByAgencyIdAndCandidateId(agencyId, candidateId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationResponse> getApplicationsByJob(UUID jobId) {

        UUID agencyId = TenantContext.getAgencyId();

        if (!jobRepository.existsByIdAndAgencyId(jobId, agencyId)) {
            throw new IllegalArgumentException("Job not found");
        }

        return applicationRepository
                .findAllByAgencyIdAndJobId(agencyId, jobId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ApplicationResponse mapToResponse(Application application) {

        return new ApplicationResponse(
                application.getId(),
                application.getAgency().getId(),
                application.getCandidate().getId(),
                application.getCandidate().getFirstName() + " "
                        + application.getCandidate().getLastName(),
                application.getJob().getId(),
                application.getJob().getTitle(),
                application.getJob().getClient().getCompanyName(),
                application.getStatus(),
                application.getSource(),
                application.getRecruiterNotes(),
                application.getAppliedAt(),
                application.getCreatedAt()
        );
    }

    @Override
    public ApplicationResponse updateApplicationStatus(
            UUID applicationId,
            UpdateApplicationStatusRequest request
    ) {
        UUID agencyId = TenantContext.getAgencyId();

        Application application = applicationRepository
                .findByIdAndAgencyId(applicationId, agencyId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Application not found")
                );

        String previousStatus = application.getStatus();

        ApplicationStatus currentStatus =
                ApplicationStatus.valueOf(application.getStatus());

        ApplicationStatus newStatus;

        try {
            newStatus = ApplicationStatus.valueOf(
                    request.getStatus().trim().toUpperCase()
            );
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Invalid application status: " + request.getStatus()
            );
        }

        if (currentStatus == newStatus) {
            throw new IllegalArgumentException(
                    "Application is already in status: " + newStatus
            );
        }

        if (!ApplicationStatusTransition.isAllowed(currentStatus, newStatus)) {
            throw new IllegalArgumentException(
                    "Invalid status transition from "
                            + currentStatus + " to " + newStatus
            );
        }

        if (previousStatus.equals(newStatus)) {
            throw new IllegalArgumentException(
                    "Application already has this status"
            );
        }

        var authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) {
            throw new IllegalStateException("Authenticated JWT user not found");
        }

        UUID userId = UUID.fromString(jwtAuth.getToken().getSubject());

        User changedBy = userRepository.findByIdAndAgencyId(userId, agencyId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found in your agency")
                );

        ApplicationStatusHistory history = new ApplicationStatusHistory();
        history.setAgency(application.getAgency());
        history.setApplication(application);
        history.setPreviousStatus(previousStatus);
        history.setNewStatus(newStatus.name());
        history.setChangedBy(changedBy);
        history.setNotes(request.getNotes());

        applicationStatusHistoryRepository.save(history);

        application.setStatus(newStatus.name());

        return mapToResponse(applicationRepository.save(application));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationStatusHistoryResponse> getApplicationStatusHistory(
            UUID applicationId
    ) {
        UUID agencyId = TenantContext.getAgencyId();

        applicationRepository.findByIdAndAgencyId(applicationId, agencyId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Application not found")
                );

        return applicationStatusHistoryRepository
                .findAllByApplicationIdAndAgencyIdOrderByChangedAtAsc(
                        applicationId,
                        agencyId
                )
                .stream()
                .map(history -> new ApplicationStatusHistoryResponse(
                        history.getId(),
                        history.getApplication().getId(),
                        history.getPreviousStatus(),
                        history.getNewStatus(),
                        history.getChangedBy() != null
                                ? history.getChangedBy().getId() : null,
                        history.getChangedBy() != null
                                ? history.getChangedBy().getFirstName() + " "
                                + history.getChangedBy().getLastName()
                                : "System",
                        history.getNotes(),
                        history.getChangedAt()
                ))
                .toList();
    }
}
