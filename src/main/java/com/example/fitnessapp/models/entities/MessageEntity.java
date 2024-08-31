package com.example.fitnessapp.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "message")
public class MessageEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "is_read")
    private Boolean isRead;
    @Basic
    @Column(name = "to_user")
    private Integer toUserId;
    @Basic
    @Column(name = "date")
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

}
