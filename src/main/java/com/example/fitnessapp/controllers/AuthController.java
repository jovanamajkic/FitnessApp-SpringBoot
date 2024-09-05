package com.example.fitnessapp.controllers;

import com.example.fitnessapp.models.dto.LoginResponse;
import com.example.fitnessapp.models.dto.User;
import com.example.fitnessapp.models.requests.LoginRequest;
import com.example.fitnessapp.models.requests.RegistrationRequest;
import com.example.fitnessapp.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("register")
    public void register(@RequestBody @Valid RegistrationRequest request){

        authService.register(request);
    }

    @PostMapping("login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request){

        return authService.login(request);
    }

    @GetMapping ("activate")
    public User activate(@RequestParam String code){
        return authService.activate(code);
    }

}
