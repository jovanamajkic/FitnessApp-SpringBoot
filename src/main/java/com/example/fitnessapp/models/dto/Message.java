package com.example.fitnessapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    private Integer id;
    private String content;
    private Boolean isRead;
    private Integer toUserId;
    private LocalDate date;
    private User user;
}
