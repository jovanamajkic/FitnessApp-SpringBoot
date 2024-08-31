package com.example.fitnessapp.controllers;

import com.example.fitnessapp.models.dto.AttributeValue;
import com.example.fitnessapp.models.dto.Program;
import com.example.fitnessapp.models.dto.ProgramHasAttributeValue;
import com.example.fitnessapp.models.requests.ProgramHasAttributeValueRequest;
import com.example.fitnessapp.services.ProgramHasAttributeValueService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/program_has_attribute_values")
public class ProgramHasAttributeValueController {
    private final ProgramHasAttributeValueService service;

    public ProgramHasAttributeValueController(ProgramHasAttributeValueService service) {
        this.service = service;
    }

    @PostMapping
    public ProgramHasAttributeValue insert(@RequestBody @Valid ProgramHasAttributeValueRequest request, Authentication auth){
        return service.insert(request, auth);
    }

    @GetMapping("/value/{valueId}")
    public List<Program> getProgramsByValue(@PathVariable Integer valueId){
        return service.findProgramsByValue(valueId);
    }

    @GetMapping("/program/{programId}")
    public List<AttributeValue> getValuesByProgram(@PathVariable Integer programId){
        return service.findValuesByProgram(programId);
    }
}
