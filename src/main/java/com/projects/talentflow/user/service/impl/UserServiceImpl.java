package com.projects.talentflow.user.service.impl;

import com.projects.talentflow.agency.entity.Agency;
import com.projects.talentflow.agency.repository.AgencyRepository;
import com.projects.talentflow.common.exception.DuplicateResourceException;
import com.projects.talentflow.common.exception.ResourceNotFoundException;
import com.projects.talentflow.common.service.impl.BaseServiceImpl;
import com.projects.talentflow.security.TenantContext;
import com.projects.talentflow.user.dto.CreateUserRequest;
import com.projects.talentflow.user.dto.UserResponse;
import com.projects.talentflow.user.entity.User;
import com.projects.talentflow.user.entity.UserRole;
import com.projects.talentflow.user.repository.UserRepository;
import com.projects.talentflow.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl
        extends BaseServiceImpl<User>
        implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final AgencyRepository agencyRepository;

    public UserServiceImpl(UserRepository userRepository, AgencyRepository agencyRepository, PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.userRepository = userRepository;
        this.agencyRepository = agencyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findByAgencyIdAndEmail(UUID agencyId, String email) {
        return userRepository.findByAgencyIdAndEmail(
                agencyId,
                email.trim().toLowerCase()
        );
    }

    @Override
    public boolean existByAgencyIdAndEmail(UUID agencyId, String email) {
        return userRepository.existsByAgencyIdAndEmail(
                agencyId,
                email.trim().toLowerCase()
        );
    }

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request, UUID agencyId) {

        if (request.getRole() == UserRole.PLATFORM_ADMIN) {
            throw new IllegalArgumentException(
                    "Agency administrators cannot create platform administrators"
            );
        }

        String email = request.getEmail().trim().toLowerCase();

        Agency agency = agencyRepository.findById(agencyId).orElseThrow(()->
                new ResourceNotFoundException("Agency not found"));

        if (existByAgencyIdAndEmail(agencyId, email)) throw new DuplicateResourceException("User email already exists in this agency");

        User user = new User();
        user.setAgency(agency);
        user.setFirstName(request.getFirstName().trim());
        user.setLastName(request.getLastName().trim());
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setStatus("ACTIVE");

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getAgency().getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                user.getStatus(),
                user.getCreatedAt()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {

        UUID agencyId = TenantContext.getAgencyId();

        return userRepository.findAllByAgencyId(agencyId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(UUID userId) {

        UUID agencyId = TenantContext.getAgencyId();

        User user = userRepository
                .findByIdAndAgencyId(userId, agencyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return mapToResponse(user);
    }
}