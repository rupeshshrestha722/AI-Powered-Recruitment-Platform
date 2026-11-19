package com.projects.talentflow.candidate.service;

import com.projects.talentflow.candidate.dto.CandidateDocumentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface CandidateDocumentService {

    CandidateDocumentResponse uploadCandidateDocument(
            UUID candidateId,
            MultipartFile file
    );

    List<CandidateDocumentResponse> getCandidateDocuments(UUID candidateId);

    CandidateDocumentResponse getDocumentById(UUID documentId);

    void deleteDocument(UUID documentId);
}