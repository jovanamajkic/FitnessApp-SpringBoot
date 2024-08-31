package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dto.Program;
import com.example.fitnessapp.models.dto.UserHasProgram;
import com.example.fitnessapp.models.requests.UserHasProgramRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserHasProgramService {
    List<Program> getByUser(Integer userId, Authentication auth);
    UserHasProgram insert(UserHasProgramRequest request, Authentication auth);
    UserHasProgram getUserHasProgram(Integer userId, Integer programId, Authentication auth);
    void updateIsCompleted();
}
