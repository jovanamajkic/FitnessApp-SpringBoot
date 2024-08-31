package com.example.fitnessapp.controllers;

import com.example.fitnessapp.exceptions.ForbiddenException;
import com.example.fitnessapp.models.dto.JwtUser;
import com.example.fitnessapp.models.dto.User;
import com.example.fitnessapp.models.requests.PasswordChangeRequest;
import com.example.fitnessapp.models.requests.UserUpdateRequest;
import com.example.fitnessapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserContorller {
    private final UserService service;

    public UserContorller(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> findAll(Authentication auth){
        return service.findAll(auth);
    }

    @PutMapping("/update/{username}")
    public User update(@PathVariable String username, @RequestBody @Valid UserUpdateRequest request,
                       Authentication auth){
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        if(!jwtUser.getUsername().equals(username)){
            throw new ForbiddenException();
        }
        return service.update(username, request, auth);
    }

    @PutMapping("/change-password/{username}")
    public User update(@PathVariable String username, @RequestBody @Valid PasswordChangeRequest request, Authentication auth){
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        if(!jwtUser.getUsername().equals(username))
            throw new ForbiddenException("You are not allowed to change the password for this user.");
        return service.changePassword(username, request, auth);
    }
}
