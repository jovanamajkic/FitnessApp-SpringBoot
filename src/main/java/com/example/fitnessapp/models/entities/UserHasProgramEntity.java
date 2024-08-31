package com.example.fitnessapp.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "user_has_program")
public class UserHasProgramEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "is_completed")
    private Boolean isCompleted;
    @Basic
    @Column(name = "start_date")
    private LocalDate startDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Program_id", referencedColumnName = "id", nullable = false)
    private ProgramEntity program;

}
