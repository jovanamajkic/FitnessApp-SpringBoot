package com.example.fitnessapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image implements Serializable {
    private Integer id;
    private String url;
    private Integer programId;
}
