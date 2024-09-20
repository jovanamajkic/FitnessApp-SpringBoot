package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.exceptions.NotFoundException;
import com.example.fitnessapp.models.dto.JwtUser;
import com.example.fitnessapp.models.dto.Program;
import com.example.fitnessapp.models.dto.UserHasProgram;
import com.example.fitnessapp.models.entities.ProgramEntity;
import com.example.fitnessapp.models.entities.UserEntity;
import com.example.fitnessapp.models.entities.UserHasProgramEntity;
import com.example.fitnessapp.models.requests.UserHasProgramRequest;
import com.example.fitnessapp.repositories.ProgramEntityRepository;
import com.example.fitnessapp.repositories.UserEntityRepository;
import com.example.fitnessapp.repositories.UserHasProgramEntityRepository;
import com.example.fitnessapp.services.UserHasProgramService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserHasProgramServiceImpl implements UserHasProgramService {
    private final UserHasProgramEntityRepository repository;
    private final UserEntityRepository userRepository;
    private final ProgramEntityRepository programRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LogManager.getLogger(UserHasProgramServiceImpl.class);

    public UserHasProgramServiceImpl(UserHasProgramEntityRepository repository, UserEntityRepository userRepository, ProgramEntityRepository programRepository, ModelMapper modelMapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.programRepository = programRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Program> getByUser(Integer userId, Authentication auth) {
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        logger.info("Find all UserHasPrograms by user " + jwtUser.getId());
        return repository.findAllByUser_Id(userId)
                .stream()
                .map(entity -> modelMapper.map(entity.getProgram(), Program.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserHasProgram insert(UserHasProgramRequest request, Authentication auth) {
        UserEntity user = userRepository.findById(request.getUserId()).orElseThrow(NotFoundException::new);
        ProgramEntity program = programRepository.findById(request.getProgramId()).orElseThrow(NotFoundException::new);
        UserHasProgramEntity entity = modelMapper.map(request, UserHasProgramEntity.class);
        entity.setUser(user);
        entity.setProgram(program);
        entity.setIsCompleted(false);
        entity.setStartDate(LocalDate.now());
        repository.saveAndFlush(entity);
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        logger.info("UserHasProgram " + entity.getId() + " insert by user " + jwtUser.getId());
        return modelMapper.map(entity, UserHasProgram.class);
    }

    @Override
    public UserHasProgram getUserHasProgram(Integer userId, Integer programId, Authentication auth) {
        UserHasProgramEntity entity = repository.findByUser_IdAndProgram_Id(userId, programId);
        return entity!= null ? modelMapper.map(entity, UserHasProgram.class) : null;
    }

    @Override
    public void updateIsCompleted() {
        List<UserHasProgramEntity> entities = repository.findAll();
        entities.forEach(entity -> {
            LocalDate today = LocalDate.now();
            long daysPassed = ChronoUnit.DAYS.between(entity.getStartDate(), today);
            int duration = entity.getProgram().getDuration();
            if(daysPassed > duration){
                entity.setIsCompleted(true);
                repository.saveAndFlush(entity);
                logger.info("Program " + entity.getProgram().getId() + " completed by user " + entity.getUser().getId());
            }
        });
    }
}
