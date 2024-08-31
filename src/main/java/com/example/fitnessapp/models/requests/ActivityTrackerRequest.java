package com.example.fitnessapp.models.requests;

import com.example.fitnessapp.models.enums.ExerciseType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ActivityTrackerRequest {
    @NotNull
    private ExerciseType exercise;
    @NotNull
    private Integer duration;
    @NotBlank
    private String intensity;
    @NotBlank
    private String result;
    @NotNull
    private BigDecimal bodyWeight;
    @NotNull
    private Integer userId;
}
