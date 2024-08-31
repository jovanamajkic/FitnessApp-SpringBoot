package com.example.fitnessapp.models.dto;

import com.example.fitnessapp.models.enums.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attribute implements Serializable {
    private Integer id;
    private String name;
    private Integer categoryId;
}
