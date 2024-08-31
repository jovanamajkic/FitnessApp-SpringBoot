package com.example.fitnessapp.models.dto;

import com.example.fitnessapp.models.enums.ExerciseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityTracker implements Serializable {
    private Integer id;
    private ExerciseType exercise;
    private Integer duration;
    private String intensity;
    private String result;
    private BigDecimal bodyWeight;
    private Date date;
    private User user;
}
