package com.example.fitnessapp.models.entities;

import com.example.fitnessapp.models.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "user")
public class UserEntity {
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
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "city")
    private String city;
    @Basic
    @Column(name = "avatar")
    private String avatar;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('REQUESTED', 'ACTIVE', 'INACTIVE')")
    private UserStatus status;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ActivityTrackerEntity> activityTrackers;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<CommentEntity> comments;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<MessageEntity> messages;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ProgramEntity> programs;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserHasCategoryEntity> userHasCategories;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserHasProgramEntity> userHasPrograms;
    @Basic
    @Column(name = "verification_code")
    private String verificationCode;

}
