package com.example.fitnessapp.models.requests;

import com.example.fitnessapp.models.dto.AttributeValue;
import com.example.fitnessapp.models.dto.Program;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProgramHasAttributeValueRequest {
    @NotNull
    private Integer programId;
    @NotNull
    private Integer attributeValueId;
}
