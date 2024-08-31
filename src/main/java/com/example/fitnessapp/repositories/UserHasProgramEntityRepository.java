package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.UserHasProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserHasProgramEntityRepository extends JpaRepository<UserHasProgramEntity, Integer> {
    List<UserHasProgramEntity> findAllByUser_Id(Integer userId);
    UserHasProgramEntity findByUser_IdAndProgram_Id(Integer userId, Integer programId);
}
