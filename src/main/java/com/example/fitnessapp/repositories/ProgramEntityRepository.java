package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.dto.Program;
import com.example.fitnessapp.models.entities.ProgramEntity;
import com.example.fitnessapp.models.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProgramEntityRepository extends JpaRepository<ProgramEntity, Integer>, JpaSpecificationExecutor<ProgramEntity> {
    Page<ProgramEntity> findAllByCategory_Name(Pageable page, String name);

    Page<ProgramEntity> findAllByTitleContainingIgnoreCase(Pageable page, String title);

    List<ProgramEntity> findAllByUser_Id(Integer userId);
    List<ProgramEntity> findAllByCategory_Id(Integer categoryId);
}
