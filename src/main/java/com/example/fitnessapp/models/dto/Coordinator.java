package com.example.fitnessapp.models.dto;

import com.example.fitnessapp.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinator implements Serializable {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Role role;

}
