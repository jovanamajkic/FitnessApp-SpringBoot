package com.example.fitnessapp.models.requests;

import com.example.fitnessapp.models.dto.Image;
import com.example.fitnessapp.models.enums.DificultyLevel;
import com.example.fitnessapp.models.enums.Location;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProgramRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private BigDecimal price;
    @NotBlank
    private DificultyLevel dificultyLevel;
    @NotNull
    private Integer duration;
    @NotBlank
    private Location location;
    @NotBlank
    private String instructor;
    @NotBlank
    private String contact;
    private String videoUrl;
    @NotNull
    private Integer categoryId;
    @NotNull
    private Integer userId;
    private List<Image> images;
}
