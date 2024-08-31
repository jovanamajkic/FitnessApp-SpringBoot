package com.example.fitnessapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHasProgram implements Serializable {
    private Integer id;
    private Boolean isCompleted;
    private LocalDate startDate;
    private Integer userId;
    private Program program;
}
