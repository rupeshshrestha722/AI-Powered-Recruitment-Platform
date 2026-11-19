package com.projects.talentflow.application.entity;

import java.util.Map;
import java.util.Set;

public final class ApplicationStatusTransition {

    private ApplicationStatusTransition() {
    }

    private static final Map<ApplicationStatus, Set<ApplicationStatus>> ALLOWED_TRANSITIONS =
            Map.ofEntries(

                    Map.entry(ApplicationStatus.SOURCED,
                            Set.of(ApplicationStatus.SCREENING, ApplicationStatus.REJECTED)),

                    Map.entry(ApplicationStatus.SCREENING,
                            Set.of(ApplicationStatus.QUALIFIED, ApplicationStatus.REJECTED)),

                    Map.entry(ApplicationStatus.QUALIFIED,
                            Set.of(ApplicationStatus.SHORTLISTED, ApplicationStatus.REJECTED)),

                    Map.entry(ApplicationStatus.SHORTLISTED,
                            Set.of(ApplicationStatus.SUBMITTED_TO_CLIENT, ApplicationStatus.REJECTED)),

                    Map.entry(ApplicationStatus.SUBMITTED_TO_CLIENT,
                            Set.of(ApplicationStatus.CLIENT_REVIEW, ApplicationStatus.REJECTED)),

                    Map.entry(ApplicationStatus.CLIENT_REVIEW,
                            Set.of(ApplicationStatus.INTERVIEW, ApplicationStatus.REJECTED)),

                    Map.entry(ApplicationStatus.INTERVIEW,
                            Set.of(ApplicationStatus.SELECTED, ApplicationStatus.REJECTED)),

                    Map.entry(ApplicationStatus.SELECTED,
                            Set.of(ApplicationStatus.OFFER, ApplicationStatus.REJECTED)),

                    Map.entry(ApplicationStatus.OFFER,
                            Set.of(ApplicationStatus.PLACED, ApplicationStatus.OFFER_DECLINED))
            );

    public static boolean isAllowed(
            ApplicationStatus current,
            ApplicationStatus next
    ) {
        return ALLOWED_TRANSITIONS
                .getOrDefault(current, Set.of())
                .contains(next);
    }
}