package com.example.fitnessapp.models.entities;

import com.example.fitnessapp.models.enums.DificultyLevel;
import com.example.fitnessapp.models.enums.Location;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "program")
public class ProgramEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "price")
    private BigDecimal price;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('BEGINNER', 'INTERMEDIATE', 'ADVANCED')")
    private DificultyLevel dificultyLevel;
    @Basic
    @Column(name = "duration")
    private Integer duration;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ONLINE', 'GYM', 'PARK')")
    private Location location;
    @Basic
    @Column(name = "instructor")
    private String instructor;
    @Basic
    @Column(name = "contact")
    private String contact;
    @Basic
    @Column(name = "video_url")
    private String videoUrl;
    @OneToMany(mappedBy = "program")
    @JsonIgnore
    private List<CommentEntity> comments;
    @OneToMany(mappedBy = "program")
    @JsonIgnore
    private List<ImageEntity> images;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Category_id", referencedColumnName = "id", nullable = false)
    private CategoryEntity category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;
    @OneToMany(mappedBy = "program")
    @JsonIgnore
    private List<UserHasProgramEntity> userHasPrograms;

}
