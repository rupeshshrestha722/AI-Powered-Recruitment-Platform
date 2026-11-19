package com.projects.talentflow.auth.service.impl;

import com.projects.talentflow.auth.dto.LoginRequest;
import com.projects.talentflow.auth.dto.LoginResponse;
import com.projects.talentflow.auth.service.AuthService;
import com.projects.talentflow.security.JwtService;
import com.projects.talentflow.user.entity.User;
import com.projects.talentflow.user.entity.UserRole;
import com.projects.talentflow.user.repository.UserRepository;
import com.projects.talentflow.user.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final UserRepository userRepository;


    public AuthServiceImpl(
            UserService userService,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userService.findByAgencyIdAndEmail(request.getAgencyId(), request.getEmail().trim().toLowerCase())
                .orElseThrow(() ->
                        new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash()))
            throw new BadCredentialsException("Invalid email or password");

        if (!"ACTIVE".equals(user.getStatus())) throw new BadCredentialsException("User account is inactive");

        user.setLastLoginAt(LocalDateTime.now());
        String token = jwtService.generateToken(user);

        return new LoginResponse(
                token,
                "Bearer",
                3600,
                user.getId(),
                user.getAgency().getId(),
                user.getEmail(),
                user.getRole().name()
        );
    }

    @Override
    @Transactional
    public LoginResponse platformLogin(LoginRequest request) {

        String email = request.getEmail().trim().toLowerCase();

        User user = userRepository
                .findByRoleAndEmailIgnoreCase(UserRole.PLATFORM_ADMIN, email)
                .orElseThrow(() ->
                        new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        if (!"ACTIVE".equals(user.getStatus())) {
            throw new BadCredentialsException("User account is inactive");
        }

        user.setLastLoginAt(LocalDateTime.now());

        String token = jwtService.generateToken(user);

        return new LoginResponse(
                token,
                "Bearer",
                3600,
                user.getId(),
                null,
                user.getEmail(),
                user.getRole().name()
        );
    }
}
