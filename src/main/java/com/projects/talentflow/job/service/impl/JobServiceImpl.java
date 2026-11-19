package com.projects.talentflow.job.service.impl;

import com.projects.talentflow.client.entity.Client;
import com.projects.talentflow.client.repository.ClientRepository;
import com.projects.talentflow.common.exception.ResourceNotFoundException;
import com.projects.talentflow.common.service.impl.BaseServiceImpl;
import com.projects.talentflow.job.dto.CreateJobRequest;
import com.projects.talentflow.job.dto.JobResponse;
import com.projects.talentflow.job.entity.Job;
import com.projects.talentflow.job.repository.JobRepository;
import com.projects.talentflow.job.service.JobService;
import com.projects.talentflow.security.TenantContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class JobServiceImpl
        extends BaseServiceImpl<Job>
        implements JobService {

    private final JobRepository jobRepository;
    private final ClientRepository clientRepository;

    public JobServiceImpl(
            JobRepository jobRepository,
            ClientRepository clientRepository) {

        super(jobRepository);
        this.jobRepository = jobRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public JobResponse createJob(CreateJobRequest request) {

        UUID agencyId = TenantContext.getAgencyId();

        // Validate that the client belongs to the logged-in agency.
        Client client = clientRepository
                .findByIdAndAgencyId(request.getClientId(), agencyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Client not found"));

        // Validate experience range.
        if (request.getMinExperience() != null
                && request.getMaxExperience() != null
                && request.getMinExperience()
                .compareTo(request.getMaxExperience()) > 0) {

            throw new IllegalArgumentException(
                    "Minimum experience cannot exceed maximum experience");
        }

        // Validate salary range.
        if (request.getMinSalary() != null
                && request.getMaxSalary() != null
                && request.getMinSalary()
                .compareTo(request.getMaxSalary()) > 0) {

            throw new IllegalArgumentException(
                    "Minimum salary cannot exceed maximum salary");
        }

        Job job = new Job();

        job.setAgency(client.getAgency());
        job.setClient(client);

        job.setTitle(request.getTitle().trim());
        job.setDescription(request.getDescription());
        job.setRequiredSkills(request.getRequiredSkills());

        job.setMinExperience(request.getMinExperience());
        job.setMaxExperience(request.getMaxExperience());

        job.setEmploymentType(request.getEmploymentType());
        job.setWorkplaceType(request.getWorkplaceType());

        job.setCountry(request.getCountry());
        job.setCity(request.getCity());

        job.setMinSalary(request.getMinSalary());
        job.setMaxSalary(request.getMaxSalary());

        job.setSalaryCurrency(
                request.getSalaryCurrency() == null
                        || request.getSalaryCurrency().isBlank()
                        ? "AED"
                        : request.getSalaryCurrency().trim().toUpperCase()
        );

        job.setOpenings(request.getOpenings());
        job.setClosingDate(request.getClosingDate());
        job.setStatus("OPEN");

        Job savedJob = jobRepository.save(job);

        return mapToResponse(savedJob);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobResponse> getAllJobs() {

        UUID agencyId = TenantContext.getAgencyId();

        return jobRepository.findAllByAgencyId(agencyId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public JobResponse getJobById(UUID jobId) {

        UUID agencyId = TenantContext.getAgencyId();

        Job job = jobRepository
                .findByIdAndAgencyId(jobId, agencyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found"));

        return mapToResponse(job);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobResponse> getJobsByStatus(String status) {

        UUID agencyId = TenantContext.getAgencyId();

        return jobRepository
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
    public List<JobResponse> getJobsByClient(UUID clientId) {

        UUID agencyId = TenantContext.getAgencyId();

        // First verify client ownership.
        clientRepository.findByIdAndAgencyId(clientId, agencyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Client not found"));

        return jobRepository
                .findAllByAgencyIdAndClientId(agencyId, clientId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private JobResponse mapToResponse(Job job) {

        return new JobResponse(
                job.getId(),
                job.getAgency().getId(),
                job.getClient().getId(),
                job.getClient().getCompanyName(),
                job.getTitle(),
                job.getDescription(),
                job.getRequiredSkills(),
                job.getMinExperience(),
                job.getMaxExperience(),
                job.getEmploymentType(),
                job.getWorkplaceType(),
                job.getCountry(),
                job.getCity(),
                job.getMinSalary(),
                job.getMaxSalary(),
                job.getSalaryCurrency(),
                job.getOpenings(),
                job.getStatus(),
                job.getClosingDate(),
                job.getCreatedAt()
        );
    }
}