package com.example.fitnessapp.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponse extends User{
    private String token;
}
