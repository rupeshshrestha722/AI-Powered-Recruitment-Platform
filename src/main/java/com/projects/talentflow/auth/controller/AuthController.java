package com.projects.talentflow.auth.controller;

import com.projects.talentflow.auth.dto.LoginRequest;
import com.projects.talentflow.auth.dto.LoginResponse;
import com.projects.talentflow.auth.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/platform-login")
    public ResponseEntity<LoginResponse> platformLogin(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.platformLogin(request));
    }
}