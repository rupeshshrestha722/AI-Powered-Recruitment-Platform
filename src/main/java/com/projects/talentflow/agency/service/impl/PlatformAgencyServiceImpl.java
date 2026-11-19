package com.projects.talentflow.agency.service.impl;

import com.projects.talentflow.agency.dto.AgencyResponse;
import com.projects.talentflow.agency.dto.CreateAgencyWithAdminRequest;
import com.projects.talentflow.agency.entity.Agency;
import com.projects.talentflow.agency.repository.AgencyRepository;
import com.projects.talentflow.agency.service.PlatformAgencyService;
import com.projects.talentflow.common.exception.DuplicateResourceException;
import com.projects.talentflow.user.entity.User;
import com.projects.talentflow.user.entity.UserRole;
import com.projects.talentflow.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlatformAgencyServiceImpl implements PlatformAgencyService {

    private final AgencyRepository agencyRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public PlatformAgencyServiceImpl(
            AgencyRepository agencyRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.agencyRepository = agencyRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public AgencyResponse createAgencyWithAdmin(
            CreateAgencyWithAdminRequest request
    ) {

        var agencyRequest = request.getAgency();

        if (agencyRequest == null) throw new IllegalArgumentException("Agency Details are required");

        String agencyEmail = agencyRequest.getEmail() == null ? null : agencyRequest.getEmail().trim();

        if (agencyRequest != null && !agencyEmail.isBlank() && agencyRepository.existsByEmail(agencyEmail))
            throw new DuplicateResourceException("Agency email already exists");

        String adminEmail = request.getAdminEmail().trim().toLowerCase();

        Agency agency = new Agency();
        agency.setName(agencyRequest.getName().trim());
        agency.setEmail(agencyEmail);
        agency.setPhone(agencyRequest.getPhone());
        agency.setCountry(agencyRequest.getCountry());
        agency.setAddress(agencyRequest.getAddress());

        Agency savedAgency = agencyRepository.save(agency);

        if (userRepository.existsByAgencyIdAndEmail(
                savedAgency.getId(), adminEmail)) {
            throw new DuplicateResourceException("Admin email already exists");
        }

        User admin = new User();
        admin.setAgency(savedAgency);
        admin.setFirstName(request.getAdminFirstName().trim());
        admin.setLastName(request.getAdminLastName().trim());
        admin.setEmail(adminEmail);
        admin.setPasswordHash(
                passwordEncoder.encode(request.getAdminPassword())
        );
        admin.setRole(UserRole.ADMIN);
        admin.setStatus("ACTIVE");

        userRepository.save(admin);

        return new AgencyResponse(
                savedAgency.getId(),
                savedAgency.getName(),
                savedAgency.getEmail(),
                savedAgency.getPhone(),
                savedAgency.getCountry(),
                savedAgency.getAddress(),
                savedAgency.getStatus(),
                savedAgency.getCreatedAt()
        );
    }
}