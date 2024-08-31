package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.AttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributeEntityRepository extends JpaRepository<AttributeEntity, Integer> {
    List<AttributeEntity> findAllByCategory_Id(Integer categoryId);
}
