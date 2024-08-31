package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageEntityRepository extends JpaRepository<MessageEntity, Integer> {
    List<MessageEntity> findAllByToUserId(Integer toUserId);
}
