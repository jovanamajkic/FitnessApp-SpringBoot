package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.ProgramHasAttributeValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramHasAttributeValueEntityRepository extends JpaRepository<ProgramHasAttributeValueEntity, Integer> {
   List<ProgramHasAttributeValueEntity> findAllByAttributeValue_Id(Integer id);
   List<ProgramHasAttributeValueEntity> findAllByProgram_Id(Integer programId);
   void deleteAllByProgram_Id(Integer programId);
}
