
package com.projects.talentflow.ai.service;

import com.projects.talentflow.ai.dto.CvParsingResponse;
import com.projects.talentflow.ai.dto.CvParsingReviewRequest;
import com.projects.talentflow.ai.dto.CvParsingReviewResponse;
import com.projects.talentflow.ai.entity.CandidateCvParsingResult;

import java.util.List;
import java.util.UUID;

public interface CandidateCvParsingResultService {

    CandidateCvParsingResult saveParsingResult(
            UUID documentId,
            CvParsingResponse response
    );

    CandidateCvParsingResult getParsingResult(UUID documentId);

    List<CandidateCvParsingResult> getAllParsingResults();

    CvParsingReviewResponse getReviewResult(UUID documentId);

    CvParsingReviewResponse updateReview(
            UUID documentId,
            CvParsingReviewRequest request
    );
}
