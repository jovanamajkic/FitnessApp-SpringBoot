package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dto.LoginResponse;
import com.example.fitnessapp.models.dto.User;
import com.example.fitnessapp.models.requests.LoginRequest;
import com.example.fitnessapp.models.requests.RegistrationRequest;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    void register(RegistrationRequest request);
    User activate(String code);
}
