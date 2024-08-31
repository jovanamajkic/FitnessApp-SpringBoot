package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dto.Program;
import com.example.fitnessapp.models.requests.ProgramRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ProgramService {
    List<Program> findAll();
    List<Program> findAllByUser(Integer userId);
    Program findById(Integer id);
    Program insert(ProgramRequest request, Authentication auth);
    void delete(Integer id, Authentication auth);
}
