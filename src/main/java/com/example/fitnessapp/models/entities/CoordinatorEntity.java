package com.example.fitnessapp.models.entities;

import com.example.fitnessapp.models.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "coordinator")
public class CoordinatorEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "first_name")
    private String firstName;
    @Basic
    @Column(name = "last_name")
    private String lastName;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ADMIN', 'CONSULTANT')")
    private Role role;

}
