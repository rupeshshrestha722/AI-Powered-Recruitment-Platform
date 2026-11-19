package com.projects.talentflow.agency.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class AgencyResponse {

    private UUID id;

    private String name;

    private String email;

    private String phone;

    private String country;

    private String address;

    private String status;

    private LocalDateTime createdAt;

    public AgencyResponse(UUID id, String name, String email, String phone, String country, String address, String status, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.address = address;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCountry() {
        return country;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
