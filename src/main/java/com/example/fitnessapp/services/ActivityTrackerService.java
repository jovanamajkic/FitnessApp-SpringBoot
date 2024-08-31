package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dto.ActivityTracker;
import com.example.fitnessapp.models.requests.ActivityTrackerRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ActivityTrackerService {
    ActivityTracker insert(ActivityTrackerRequest request, Authentication auth);
    Page<ActivityTracker> findByUser(Pageable page, Integer userId, Authentication auth);
    List<ActivityTracker> findAllByUser(Integer userId, Authentication auth);
}
