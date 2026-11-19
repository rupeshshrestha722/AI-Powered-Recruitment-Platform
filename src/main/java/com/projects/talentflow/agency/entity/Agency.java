package com.projects.talentflow.agency.entity;

import com.projects.talentflow.common.entity.BaseEntity;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "agencies")
public class Agency extends BaseEntity {


    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 255, unique = true)
    private String email;

    @Column(length = 30)
    private String phone;

    @Column(length = 100)
    private String country;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(nullable = false, length = 30)
    private String status = "ACTIVE";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
