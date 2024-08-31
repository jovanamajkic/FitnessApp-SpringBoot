package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dto.UserHasCategory;
import com.example.fitnessapp.models.requests.UserHasCategoryRequest;
import org.springframework.security.core.Authentication;

public interface UserHasCategoryService {
    UserHasCategory insert(UserHasCategoryRequest request, Authentication auth);
    void delete(Integer userId, Integer categoryId, Authentication auth);
    boolean userHasCategoryExists(Integer userId, Integer categoryId, Authentication auth);
    void sendMail();
}
