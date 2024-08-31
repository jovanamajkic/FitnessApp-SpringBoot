package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentEntityRepository extends JpaRepository<CommentEntity, Integer> {
    Page<CommentEntity> findAllByProgram_Id(Pageable page, Integer programId);
}
