package com.example.fitnessapp.controllers;

import com.example.fitnessapp.exceptions.NotFoundException;
import com.example.fitnessapp.exceptions.UnauthorizedException;
import com.example.fitnessapp.models.dto.LoginResponse;
import com.example.fitnessapp.models.dto.User;
import com.example.fitnessapp.models.entities.UserEntity;
import com.example.fitnessapp.models.requests.LoginRequest;
import com.example.fitnessapp.models.requests.RegistrationRequest;
import com.example.fitnessapp.services.AuthService;
import com.example.fitnessapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
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
        User user = authService.activate(code);
        System.out.println("Activated user: " + user);
        return user;
    }

}
