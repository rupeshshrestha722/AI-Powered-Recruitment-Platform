package com.projects.talentflow.config;

import com.projects.talentflow.user.entity.User;
import com.projects.talentflow.user.entity.UserRole;
import com.projects.talentflow.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PlatformAdminBootstrap {

    @Bean
    CommandLineRunner createPlatformAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.bootstrap.platform-admin.enabled:false}") boolean enabled,
            @Value("${app.bootstrap.platform-admin.first-name:}") String firstName,
            @Value("${app.bootstrap.platform-admin.last-name:}") String lastName,
            @Value("${app.bootstrap.platform-admin.email:}") String email,
            @Value("${app.bootstrap.platform-admin.password:}") String password) {
        return args -> {

            if (!enabled) {
                return;
            }

            if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.length() < 12) {
                throw new IllegalStateException(
                        "Platform Admin bootstrap requires names, email and a password of at least 12 characters"
                );
            }

            String normalizedEmail = email.trim().toLowerCase();

            if (userRepository.existsByRoleAndEmailIgnoreCase(UserRole.PLATFORM_ADMIN, normalizedEmail)) {
                System.out.println("Platform Admin already exists. Bootstrap skipped.");
                return;
            }

            User admin = new User();
            admin.setFirstName(firstName.trim());
            admin.setLastName(lastName.trim());
            admin.setEmail(normalizedEmail);
            admin.setPasswordHash(passwordEncoder.encode(password));
            admin.setRole(UserRole.PLATFORM_ADMIN);
            admin.setStatus("ACTIVE");
            admin.setAgency(null);

            userRepository.save(admin);

            System.out.println("Platform Admin created successfully.");

        };
    }
}
