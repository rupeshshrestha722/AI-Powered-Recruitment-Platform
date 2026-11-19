package com.projects.talentflow.client.entity;

import com.projects.talentflow.agency.entity.Agency;
import com.projects.talentflow.common.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "clients", indexes = {
        @Index(name = "idx_clients_agency_id", columnList = "agency_id"),
        @Index(name = "idx_clients_agency_company", columnList = "agency_id, company_name"),
        @Index(name = "idx_clients_agency_email", columnList = "agency_id, email")
})
public class Client extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "agency_id", nullable = false)
    private Agency agency;

    @Column(name = "company_name", nullable = false, length = 200)
    private String companyName;

    @Column(length = 150)
    private String industry;

    @Column(length = 255)
    private String email;

    @Column(length = 30)
    private String phone;

    @Column(length = 500)
    private String website;

    @Column(length = 100)
    private String country;

    @Column(length = 100)
    private String city;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(nullable = false, length = 30)
    private String status = "ACTIVE";

    public Client() {
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}