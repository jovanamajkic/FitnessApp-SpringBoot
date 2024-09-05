package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.exceptions.NotFoundException;
import com.example.fitnessapp.models.dto.ActivityTracker;
import com.example.fitnessapp.models.dto.JwtUser;
import com.example.fitnessapp.models.entities.ActivityTrackerEntity;
import com.example.fitnessapp.models.entities.UserEntity;
import com.example.fitnessapp.models.requests.ActivityTrackerRequest;
import com.example.fitnessapp.repositories.ActivityTrackerEntityRepository;
import com.example.fitnessapp.repositories.UserEntityRepository;
import com.example.fitnessapp.services.ActivityTrackerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityTrackerServiceImpl implements ActivityTrackerService {
    private final ActivityTrackerEntityRepository repository;
    private final UserEntityRepository userRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LogManager.getLogger(ActivityTrackerServiceImpl.class);


    public ActivityTrackerServiceImpl(ActivityTrackerEntityRepository repository, UserEntityRepository userRepository, ModelMapper modelMapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ActivityTracker insert(ActivityTrackerRequest request, Authentication auth) {
        ActivityTrackerEntity entity = modelMapper.map(request, ActivityTrackerEntity.class);
        UserEntity user = userRepository.findById(request.getUserId()).orElseThrow(NotFoundException::new);
        entity.setUser(user);
        entity.setDate(Date.valueOf(LocalDate.now()));
        repository.saveAndFlush(entity);
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        logger.info("Activity tracker " + entity.getId() + " insert by user " + jwtUser.getId());
        return modelMapper.map(entity, ActivityTracker.class);
    }

    @Override
    public Page<ActivityTracker> findByUser(Pageable page, Integer userId, Authentication auth) {
        return repository.findAllByUser_Id(page, userId)
                .map(entity -> modelMapper.map(entity, ActivityTracker.class));
    }

    @Override
    public List<ActivityTracker> findAllByUser(Integer userId, Authentication auth) {
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        logger.info("Find all activity trackers by user " + jwtUser.getId());
        return repository.findAllByUser_Id(userId)
                .stream()
                .map(entity -> modelMapper.map(entity, ActivityTracker.class))
                .sorted(Comparator.comparing(ActivityTracker::getDate).reversed())
                .collect(Collectors.toList());
    }
}
