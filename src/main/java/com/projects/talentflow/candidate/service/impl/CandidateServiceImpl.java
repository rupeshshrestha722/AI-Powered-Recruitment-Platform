package com.projects.talentflow.candidate.service.impl;

import com.projects.talentflow.agency.repository.AgencyRepository;
import com.projects.talentflow.candidate.dto.CandidateResponse;
import com.projects.talentflow.candidate.dto.CreateCandidateRequest;
import com.projects.talentflow.candidate.entity.Candidate;
import com.projects.talentflow.candidate.repository.CandidateRepository;
import com.projects.talentflow.candidate.service.CandidateService;
import com.projects.talentflow.common.exception.DuplicateResourceException;
import com.projects.talentflow.common.exception.ResourceNotFoundException;
import com.projects.talentflow.common.service.impl.BaseServiceImpl;
import com.projects.talentflow.security.TenantContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CandidateServiceImpl
        extends BaseServiceImpl<Candidate>
        implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final AgencyRepository agencyRepository;

    public CandidateServiceImpl(
            CandidateRepository candidateRepository,
            AgencyRepository agencyRepository) {

        super(candidateRepository);
        this.candidateRepository = candidateRepository;
        this.agencyRepository = agencyRepository;
    }

    @Override
    public CandidateResponse createCandidate(CreateCandidateRequest request) {

        UUID agencyId = TenantContext.getAgencyId();

        var agency = agencyRepository.findById(agencyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Agency not found"));

        String email = request.getEmail() == null
                ? null
                : request.getEmail().trim().toLowerCase();

        if (email != null && !email.isBlank()
                && candidateRepository.existsByAgencyIdAndEmail(agencyId, email)) {
            throw new DuplicateResourceException(
                    "Candidate with this email already exists");
        }

        Candidate candidate = new Candidate();
        candidate.setAgency(agency);
        candidate.setFirstName(request.getFirstName().trim());
        candidate.setLastName(request.getLastName().trim());
        candidate.setEmail(email);
        candidate.setPhone(request.getPhone());
        candidate.setLocation(request.getLocation());
        candidate.setCurrentDesignation(request.getCurrentDesignation());
        candidate.setCurrentCompany(request.getCurrentCompany());
        candidate.setTotalExperience(request.getTotalExperience());
        candidate.setNoticePeriod(request.getNoticePeriod());
        candidate.setLinkedinUrl(request.getLinkedinUrl());
        candidate.setSummary(request.getSummary());
        candidate.setStatus("ACTIVE");

        Candidate savedCandidate = candidateRepository.save(candidate);

        return mapToResponse(savedCandidate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CandidateResponse> getAllCandidates() {

        UUID agencyId = TenantContext.getAgencyId();

        return candidateRepository.findAllByAgencyId(agencyId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateResponse getCandidateById(UUID candidateId) {

        UUID agencyId = TenantContext.getAgencyId();

        Candidate candidate = candidateRepository
                .findByIdAndAgencyId(candidateId, agencyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Candidate not found"));

        return mapToResponse(candidate);
    }

    private CandidateResponse mapToResponse(Candidate candidate) {

        return new CandidateResponse(
                candidate.getId(),
                candidate.getAgency().getId(),
                candidate.getFirstName(),
                candidate.getLastName(),
                candidate.getEmail(),
                candidate.getPhone(),
                candidate.getLocation(),
                candidate.getCurrentDesignation(),
                candidate.getCurrentCompany(),
                candidate.getTotalExperience(),
                candidate.getNoticePeriod(),
                candidate.getLinkedinUrl(),
                candidate.getSummary(),
                candidate.getStatus(),
                candidate.getCreatedAt()
        );
    }
}