package com.example.fitnessapp.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class PurchaseRequest {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer programId;
}
