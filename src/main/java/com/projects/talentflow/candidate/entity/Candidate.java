package com.projects.talentflow.candidate.entity;

import com.projects.talentflow.common.entity.BaseEntity;
import com.projects.talentflow.agency.entity.Agency;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "candidates", indexes = {
        @Index(name = "idx_candidates_agency_id", columnList = "agency_id"),
        @Index(name = "idx_candidates_agency_email", columnList = "agency_id, email"),
        @Index(name = "idx_candidates_agency_name", columnList = "agency_id, first_name, last_name")
})
public class Candidate extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "agency_id", nullable = false)
    private Agency agency;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(length = 255)
    private String email;

    @Column(length = 30)
    private String phone;

    @Column(length = 150)
    private String location;

    @Column(name = "current_designation", length = 150)
    private String currentDesignation;

    @Column(name = "current_company", length = 150)
    private String currentCompany;

    @Column(name = "total_experience", precision = 4, scale = 1)
    private BigDecimal totalExperience;

    @Column(name = "notice_period", length = 100)
    private String noticePeriod;

    @Column(name = "linkedin_url", length = 500)
    private String linkedinUrl;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(length = 30, nullable = false)
    private String status = "ACTIVE";

    public Candidate() {
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCurrentDesignation() {
        return currentDesignation;
    }

    public void setCurrentDesignation(String currentDesignation) {
        this.currentDesignation = currentDesignation;
    }

    public String getCurrentCompany() {
        return currentCompany;
    }

    public void setCurrentCompany(String currentCompany) {
        this.currentCompany = currentCompany;
    }

    public BigDecimal getTotalExperience() {
        return totalExperience;
    }

    public void setTotalExperience(BigDecimal totalExperience) {
        this.totalExperience = totalExperience;
    }

    public String getNoticePeriod() {
        return noticePeriod;
    }

    public void setNoticePeriod(String noticePeriod) {
        this.noticePeriod = noticePeriod;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}