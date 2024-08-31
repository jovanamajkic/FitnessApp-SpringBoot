package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.ActivityTrackerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityTrackerEntityRepository extends JpaRepository<ActivityTrackerEntity, Integer> {
    Page<ActivityTrackerEntity> findAllByUser_Id(Pageable page, Integer userId);
    List<ActivityTrackerEntity> findAllByUser_Id(Integer userId);
}
