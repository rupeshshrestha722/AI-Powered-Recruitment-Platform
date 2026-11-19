
package com.projects.talentflow.ai.dto;

import java.util.List;

public record CvParsingResponse(

        String fullName,

        String email,

        String phone,

        String location,

        String linkedinUrl,

        String currentDesignation,

        String currentCompany,

        Double totalExperience,

        String noticePeriod,

        String professionalSummary,

        List<String> technicalSkills,

        List<String> softSkills,

        List<EmploymentHistory> employmentHistory,

        List<Education> education,

        List<String> certifications

) {

    public record EmploymentHistory(
            String companyName,
            String designation,
            String startDate,
            String endDate,
            String responsibilities
    ) {}

    public record Education(
            String institution,
            String degree,
            String fieldOfStudy,
            String graduationYear
    ) {}
}
