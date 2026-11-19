package com.projects.talentflow.ai.dto;

import jakarta.validation.constraints.NotNull;

public record CvParsingApprovalRequest(

        @NotNull(message = "Approval decision is required")
        Boolean approved

) {
}