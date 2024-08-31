package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.UserHasCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHasCategoryEntityRepository extends JpaRepository<UserHasCategoryEntity, Integer> {
    UserHasCategoryEntity findByUser_IdAndCategory_Id(Integer userId, Integer categoryId);
    boolean existsByUser_IdAndCategory_Id(Integer userId, Integer categoryId);
}
