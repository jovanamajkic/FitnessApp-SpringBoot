package com.example.fitnessapp.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "user_has_category")
public class UserHasCategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Category_id", referencedColumnName = "id", nullable = false)
    private CategoryEntity category;

}
