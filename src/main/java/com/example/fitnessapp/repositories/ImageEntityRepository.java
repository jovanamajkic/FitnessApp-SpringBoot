package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageEntityRepository extends JpaRepository<ImageEntity, Integer> {
}
