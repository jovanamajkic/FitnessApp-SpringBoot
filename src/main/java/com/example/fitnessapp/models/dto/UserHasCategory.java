package com.example.fitnessapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHasCategory implements Serializable {
    private Integer id;
    private User user;
    private Category category;
}
