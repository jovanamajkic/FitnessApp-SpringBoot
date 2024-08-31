package com.example.fitnessapp.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserHasCategoryRequest {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer categoryId;
}
