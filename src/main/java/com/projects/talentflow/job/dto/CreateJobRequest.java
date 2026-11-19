package com.projects.talentflow.job.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class CreateJobRequest {

    @NotNull(message = "Client ID is required")
    private UUID clientId;

    @NotBlank(message = "Job title is required")
    @Size(max = 200)
    private String title;

    private String description;

    private String requiredSkills;

    @DecimalMin(value = "0.0", message = "Minimum experience cannot be negative")
    private BigDecimal minExperience;

    @DecimalMin(value = "0.0", message = "Maximum experience cannot be negative")
    private BigDecimal maxExperience;

    @Size(max = 50)
    private String employmentType;

    @Size(max = 30)
    private String workplaceType;

    @Size(max = 100)
    private String country;

    @Size(max = 100)
    private String city;

    @DecimalMin(value = "0.0", message = "Minimum salary cannot be negative")
    private BigDecimal minSalary;

    @DecimalMin(value = "0.0", message = "Maximum salary cannot be negative")
    private BigDecimal maxSalary;

    @Size(max = 10)
    private String salaryCurrency = "AED";

    @NotNull
    @Min(value = 1, message = "At least one opening is required")
    private Integer openings = 1;

    private LocalDate closingDate;

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public BigDecimal getMinExperience() {
        return minExperience;
    }

    public void setMinExperience(BigDecimal minExperience) {
        this.minExperience = minExperience;
    }

    public BigDecimal getMaxExperience() {
        return maxExperience;
    }

    public void setMaxExperience(BigDecimal maxExperience) {
        this.maxExperience = maxExperience;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getWorkplaceType() {
        return workplaceType;
    }

    public void setWorkplaceType(String workplaceType) {
        this.workplaceType = workplaceType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BigDecimal getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(BigDecimal minSalary) {
        this.minSalary = minSalary;
    }

    public BigDecimal getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(BigDecimal maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getSalaryCurrency() {
        return salaryCurrency;
    }

    public void setSalaryCurrency(String salaryCurrency) {
        this.salaryCurrency = salaryCurrency;
    }

    public Integer getOpenings() {
        return openings;
    }

    public void setOpenings(Integer openings) {
        this.openings = openings;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }
}