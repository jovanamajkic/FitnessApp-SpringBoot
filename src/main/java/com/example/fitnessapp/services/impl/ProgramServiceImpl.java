package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.exceptions.NotFoundException;
import com.example.fitnessapp.exceptions.UnauthorizedException;
import com.example.fitnessapp.models.dto.JwtUser;
import com.example.fitnessapp.models.dto.Program;
import com.example.fitnessapp.models.entities.*;
import com.example.fitnessapp.models.requests.ProgramRequest;
import com.example.fitnessapp.repositories.*;
import com.example.fitnessapp.services.ProgramService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProgramServiceImpl implements ProgramService {
    private final ProgramEntityRepository programRepository;
    private final CategoryEntityRepository categoryRepository;
    private final UserEntityRepository userRepository;
    private final ImageEntityRepository imageRepository;
    private final ProgramHasAttributeValueEntityRepository hasAttributeValueEntityRepository;
    private final CommentEntityRepository commentRepository;
    private final UserHasProgramEntityRepository userHasProgramRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LogManager.getLogger(ProgramServiceImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    public ProgramServiceImpl(ProgramEntityRepository repository, CategoryEntityRepository categoryRepository, UserEntityRepository userRepository, ImageEntityRepository imageRepository, ProgramHasAttributeValueEntityRepository hasAttributeValueEntityRepository, CommentEntityRepository commentRepository, UserHasProgramEntityRepository userHasProgramRepository, ModelMapper modelMapper) {
        this.programRepository = repository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.hasAttributeValueEntityRepository = hasAttributeValueEntityRepository;
        this.commentRepository = commentRepository;
        this.userHasProgramRepository = userHasProgramRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Program> findAll() {
        return programRepository.findAll()
                .stream()
                .map(programEntity -> modelMapper.map(programEntity, Program.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Program> findAllByUser(Integer userId) {
        return programRepository.findAllByUser_Id(userId)
                .stream()
                .map(programEntity -> modelMapper.map(programEntity, Program.class))
                .collect(Collectors.toList());
    }

    @Override
    public Program findById(Integer id) {
        ProgramEntity entity = this.programRepository.findById(id).orElseThrow(NotFoundException::new);
        return modelMapper.map(entity, Program.class);
    }

    @Override
    public Program insert(ProgramRequest request, Authentication auth) {
        CategoryEntity category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        ProgramEntity program = modelMapper.map(request, ProgramEntity.class);
        program.setCategory(category);
        program.setUser(user);
        programRepository.saveAndFlush(program);

        if(!program.getImages().isEmpty()){
            for(ImageEntity image: program.getImages()){
                image.setProgram(program);
            }
            imageRepository.saveAllAndFlush(program.getImages());
            entityManager.refresh(program);
        }

        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        logger.info("Program " + program.getId() + " insert by user " + jwtUser.getId());

        return modelMapper.map(program, Program.class);
    }

    @Override
    @Transactional
    public void delete(Integer id, Authentication auth) {
        if(auth == null || !auth.isAuthenticated()){
            throw new UnauthorizedException();
        }

        ProgramEntity program = programRepository.findById(id).orElseThrow(NotFoundException::new);

        if(!program.getUser().getUsername().equals(auth.getName())){
            throw new UnauthorizedException("Unauthorized deletion");
        }

        imageRepository.deleteAll(program.getImages());
        commentRepository.deleteAll(program.getComments());
        userHasProgramRepository.deleteAll(program.getUserHasPrograms());
        hasAttributeValueEntityRepository.deleteAllByProgram_Id(id);

        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        logger.info("Program " + program.getId() + " delete by user " + jwtUser.getId());

        programRepository.deleteById(id);
    }
}
