package com.example.fitnessapp.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordChangeRequest {
    @NotBlank
    private String currentPassword;
    @NotBlank
    private String newPassword;
}
