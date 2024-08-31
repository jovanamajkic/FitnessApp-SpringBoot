package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dto.User;
import com.example.fitnessapp.models.entities.UserEntity;
import com.example.fitnessapp.models.requests.PasswordChangeRequest;
import com.example.fitnessapp.models.requests.UserUpdateRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    UserEntity findById(Integer id);
    User update(String username, UserUpdateRequest request, Authentication auth);
    List<User> findAll(Authentication auth);
    User changePassword(String username, PasswordChangeRequest request, Authentication auth);
}
