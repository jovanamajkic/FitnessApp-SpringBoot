package com.example.fitnessapp.models.entities;

import com.example.fitnessapp.models.enums.ExerciseType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Entity
@Table(name = "activity_tracker")
public class ActivityTrackerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('CARDIO', 'STRENGTH', 'FLEXIBILITY', 'HIIT')")
    private ExerciseType exercise;
    @Basic
    @Column(name = "duration")
    private Integer duration;
    @Basic
    @Column(name = "intensity")
    private String intensity;
    @Basic
    @Column(name = "result")
    private String result;
    @Basic
    @Column(name = "body_weight")
    private BigDecimal bodyWeight;
    @Basic
    @Column(name = "date")
    private Date date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

}
