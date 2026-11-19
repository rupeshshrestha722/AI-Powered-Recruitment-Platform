package com.projects.talentflow.job.entity;

import com.projects.talentflow.agency.entity.Agency;
import com.projects.talentflow.client.entity.Client;
import com.projects.talentflow.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "jobs", indexes = {
        @Index(name = "idx_jobs_agency_id", columnList = "agency_id"),
        @Index(name = "idx_jobs_agency_client", columnList = "agency_id, client_id"),
        @Index(name = "idx_jobs_agency_status", columnList = "agency_id, status"),
        @Index(name = "idx_jobs_agency_title", columnList = "agency_id, title")
})
public class Job extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "agency_id", nullable = false)
    private Agency agency;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "client_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Client client;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "required_skills", columnDefinition = "TEXT")
    private String requiredSkills;

    @Column(name = "min_experience", precision = 4, scale = 1)
    private BigDecimal minExperience;

    @Column(name = "max_experience", precision = 4, scale = 1)
    private BigDecimal maxExperience;

    @Column(name = "employment_type", length = 50)
    private String employmentType;

    @Column(name = "workplace_type", length = 30)
    private String workplaceType;

    @Column(length = 100)
    private String country;

    @Column(length = 100)
    private String city;

    @Column(name = "min_salary", precision = 12, scale = 2)
    private BigDecimal minSalary;

    @Column(name = "max_salary", precision = 12, scale = 2)
    private BigDecimal maxSalary;

    @Column(name = "salary_currency", length = 10)
    private String salaryCurrency = "AED";

    @Column(nullable = false)
    private Integer openings = 1;

    @Column(nullable = false, length = 30)
    private String status = "OPEN";

    @Column(name = "closing_date")
    private LocalDate closingDate;

    public Job() {
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }
}