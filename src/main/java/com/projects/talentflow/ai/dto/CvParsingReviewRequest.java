
package com.projects.talentflow.ai.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CvParsingReviewRequest(

        @NotNull(message = "Reviewed CV data is required")
        @Valid
        CvParsingResponse reviewedData

) {
}
