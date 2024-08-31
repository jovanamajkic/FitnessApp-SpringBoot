package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.AttributeValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributeValueEntityRepository extends JpaRepository<AttributeValueEntity, Integer> {
    List<AttributeValueEntity> findAllByAttribute_Id(Integer attributeId);
}
