package com.projects.talentflow.candidate.service;

import com.projects.talentflow.candidate.dto.CandidateResponse;
import com.projects.talentflow.candidate.dto.CreateCandidateRequest;
import com.projects.talentflow.candidate.entity.Candidate;
import com.projects.talentflow.common.service.BaseService;

import java.util.List;
import java.util.UUID;

public interface CandidateService extends BaseService<Candidate> {

    CandidateResponse createCandidate(CreateCandidateRequest request);

    List<CandidateResponse> getAllCandidates();

    CandidateResponse getCandidateById(UUID candidateId);
}