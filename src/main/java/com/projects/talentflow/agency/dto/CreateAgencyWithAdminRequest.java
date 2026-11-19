package com.projects.talentflow.agency.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateAgencyWithAdminRequest {

    @Valid
    private CreateAgencyRequest agency;

    @NotBlank
    @Size(max = 100)
    private String adminFirstName;

    @NotBlank
    @Size(max = 100)
    private String adminLastName;

    @NotBlank
    @Email
    @Size(max = 255)
    private String adminEmail;

    @NotBlank
    @Size(min = 8, max = 72)
    private String adminPassword;

    public CreateAgencyRequest getAgency() {
        return agency;
    }

    public void setAgency(CreateAgencyRequest agency) {
        this.agency = agency;
    }

    public String getAdminFirstName() {
        return adminFirstName;
    }

    public void setAdminFirstName(String adminFirstName) {
        this.adminFirstName = adminFirstName;
    }

    public String getAdminLastName() {
        return adminLastName;
    }

    public void setAdminLastName(String adminLastName) {
        this.adminLastName = adminLastName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
