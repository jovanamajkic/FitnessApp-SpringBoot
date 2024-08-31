package com.example.fitnessapp.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "image")
public class ImageEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "url")
    private String url;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Program_id", referencedColumnName = "id", nullable = false)
    private ProgramEntity program;

}
