package com.projects.talentflow.storage.config;

import com.projects.talentflow.storage.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageInitializer {

    @Bean
    public CommandLineRunner initializeStorage(
            StorageService storageService
    ) {
        return args -> storageService.initializeBucket();
    }
}