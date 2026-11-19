package com.projects.talentflow.auth.service;

import com.projects.talentflow.auth.dto.LoginRequest;
import com.projects.talentflow.auth.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    LoginResponse platformLogin(LoginRequest request);
}
