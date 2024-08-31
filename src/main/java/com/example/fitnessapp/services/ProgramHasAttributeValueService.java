package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dto.AttributeValue;
import com.example.fitnessapp.models.dto.Program;
import com.example.fitnessapp.models.dto.ProgramHasAttributeValue;
import com.example.fitnessapp.models.entities.ProgramHasAttributeValueEntity;
import com.example.fitnessapp.models.requests.ProgramHasAttributeValueRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ProgramHasAttributeValueService {
    ProgramHasAttributeValue insert(ProgramHasAttributeValueRequest request, Authentication auth);
    List<Program> findProgramsByValue(Integer valueId);
    List<AttributeValue> findValuesByProgram(Integer programId);
}
