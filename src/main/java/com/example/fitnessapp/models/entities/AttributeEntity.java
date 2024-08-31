package com.example.fitnessapp.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "attribute")
public class AttributeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Category_id", referencedColumnName = "id", nullable = false)
    private CategoryEntity category;

}
