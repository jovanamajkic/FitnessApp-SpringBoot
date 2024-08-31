package com.example.fitnessapp.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "category")
public class CategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<AttributeEntity> attributes;
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<ProgramEntity> programs;
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<UserHasCategoryEntity> userHasCategories;

}
