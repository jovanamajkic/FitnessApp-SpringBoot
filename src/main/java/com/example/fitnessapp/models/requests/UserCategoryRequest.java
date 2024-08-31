package com.example.fitnessapp.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCategoryRequest {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer categoryId;
}
