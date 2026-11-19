
package com.projects.talentflow.ai.service.impl;

import com.projects.talentflow.ai.dto.CvParsingReviewRequest;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import com.projects.talentflow.ai.dto.CvParsingResponse;
import com.projects.talentflow.ai.dto.CvParsingReviewResponse;
import com.projects.talentflow.ai.entity.CandidateCvParsingResult;
import com.projects.talentflow.ai.repository.CandidateCvParsingResultRepository;
import com.projects.talentflow.ai.service.CandidateCvParsingResultService;


import com.projects.talentflow.security.TenantContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
@Transactional
public class CandidateCvParsingResultServiceImpl
        implements CandidateCvParsingResultService {

    private static final String MODEL_NAME = "llama3.1:8b";

    private final CandidateCvParsingResultRepository repository;
    private final ObjectMapper objectMapper;

    public CandidateCvParsingResultServiceImpl(
            CandidateCvParsingResultRepository repository,
            ObjectMapper objectMapper
    ) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public CandidateCvParsingResult saveParsingResult(
            UUID documentId,
            CvParsingResponse response
    ) {
        UUID agencyId = TenantContext.getAgencyId();

        try {
            Map<String, Object> json = objectMapper.convertValue(
                    response,
                    new tools.jackson.core.type.TypeReference<Map<String, Object>>() {}
            );
            CandidateCvParsingResult result = repository
                    .findByCandidateDocumentIdAndAgencyId(
                            documentId,
                            agencyId
                    )
                    .orElseGet(CandidateCvParsingResult::new);

            result.setAgencyId(agencyId);
            result.setCandidateDocumentId(documentId);
            result.setParsedData(json);
            result.setModelName(MODEL_NAME);
            result.setParsingStatus("COMPLETED");

            return repository.save(result);

        } catch (JacksonException exception) {
            throw new IllegalStateException(
                    "Unable to serialize CV parsing result",
                    exception
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateCvParsingResult getParsingResult(UUID documentId) {

        UUID agencyId = TenantContext.getAgencyId();

        return repository
                .findByCandidateDocumentIdAndAgencyId(
                        documentId,
                        agencyId
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "CV parsing result not found"
                        )
                );
    }

    @Override
    @Transactional(readOnly = true)
    public List<CandidateCvParsingResult> getAllParsingResults() {

        UUID agencyId = TenantContext.getAgencyId();

        return repository.findAllByAgencyId(agencyId);
    }

    private CvParsingReviewResponse mapToReviewResponse(
            CandidateCvParsingResult result
    ) {
        try {
            CvParsingResponse parsedData = objectMapper.convertValue(
                    result.getParsedData(),
                    CvParsingResponse.class
            );

            return new CvParsingReviewResponse(
                    result.getId(),
                    result.getCandidateDocumentId(),
                    result.getParsingStatus(),
                    result.getReviewStatus(),
                    result.getReviewedBy() != null
                            ? result.getReviewedBy().toString()
                            : null,
                    result.getReviewedAt(),
                    parsedData
            );

        } catch (JacksonException exception) {
            throw new IllegalStateException(
                    "Unable to read saved CV parsing result",
                    exception
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CvParsingReviewResponse getReviewResult(UUID documentId) {

        UUID agencyId = TenantContext.getAgencyId();

        CandidateCvParsingResult result = repository
                .findByCandidateDocumentIdAndAgencyId(
                        documentId,
                        agencyId
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "CV parsing result not found"
                        )
                );

        return mapToReviewResponse(result);
    }


    @Override
    public CvParsingReviewResponse updateReview(
            UUID documentId,
            CvParsingReviewRequest request
    ) {
        UUID agencyId = TenantContext.getAgencyId();

        CandidateCvParsingResult result = repository
                .findByCandidateDocumentIdAndAgencyId(
                        documentId,
                        agencyId
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "CV parsing result not found"
                        )
                );

        if ("APPROVED".equals(result.getReviewStatus())) {
            throw new IllegalArgumentException(
                    "Approved CV parsing results cannot be edited"
            );
        }

        try {
            Map<String, Object> json = objectMapper.convertValue(
                    request.reviewedData(),
                    new tools.jackson.core.type.TypeReference<Map<String, Object>>() {}
            );

            result.setParsedData(json);
            result.setReviewStatus("REVIEWED");

            // Clear previous reviewer approval metadata.
            result.setReviewedBy(null);
            result.setReviewedAt(null);

            CandidateCvParsingResult updatedResult =
                    repository.save(result);

            return mapToReviewResponse(updatedResult);

        } catch (JacksonException exception) {
            throw new IllegalStateException(
                    "Unable to update CV review data",
                    exception
            );
        }
    }

}
