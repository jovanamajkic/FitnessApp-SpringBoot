package com.example.fitnessapp.models.dto;

import com.example.fitnessapp.models.enums.DificultyLevel;
import com.example.fitnessapp.models.enums.Location;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Program implements Serializable {
    private Integer id;
    private String title;
    private String description;
    private BigDecimal price;
    private DificultyLevel dificultyLevel;
    private Integer duration;
    private Location location;
    private String instructor;
    private String contact;
    private String videoUrl;
    @JsonIgnore
    private List<Comment> comments;
    private List<Image> images;
    private Category category;
    private User user;
}
