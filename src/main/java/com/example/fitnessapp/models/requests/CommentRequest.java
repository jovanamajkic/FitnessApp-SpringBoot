package com.example.fitnessapp.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequest {
    @NotBlank
    private String content;
    @NotNull
    private Integer userId;
    @NotNull
    private Integer programId;
}
