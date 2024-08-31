package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.exceptions.NotFoundException;
import com.example.fitnessapp.models.dto.Comment;
import com.example.fitnessapp.models.dto.JwtUser;
import com.example.fitnessapp.models.entities.CommentEntity;
import com.example.fitnessapp.models.entities.ProgramEntity;
import com.example.fitnessapp.models.entities.UserEntity;
import com.example.fitnessapp.models.requests.CommentRequest;
import com.example.fitnessapp.repositories.CommentEntityRepository;
import com.example.fitnessapp.repositories.ProgramEntityRepository;
import com.example.fitnessapp.repositories.UserEntityRepository;
import com.example.fitnessapp.services.CommentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentEntityRepository commentRepository;
    private final UserEntityRepository userRepository;
    private final ProgramEntityRepository programRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LogManager.getLogger(CommentServiceImpl.class);

    public CommentServiceImpl(CommentEntityRepository commentRepository, UserEntityRepository userRepository, ProgramEntityRepository programRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.programRepository = programRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Comment insert(CommentRequest request, Authentication auth) {
        UserEntity user = userRepository.findById(request.getUserId()).orElseThrow(NotFoundException::new);
        ProgramEntity program = programRepository.findById(request.getProgramId()).orElseThrow(NotFoundException::new);

        CommentEntity entity = modelMapper.map(request, CommentEntity.class);
        entity.setUser(user);
        entity.setProgram(program);
        entity.setDateTime(LocalDateTime.now());

        commentRepository.saveAndFlush(entity);

        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        logger.info("Comment " + entity.getId() + " insert by user " + jwtUser.getId());

        return modelMapper.map(entity, Comment.class);
    }

    @Override
    public Page<Comment> findAllByProgram(Pageable page, Integer programId) {
        return commentRepository.findAllByProgram_Id(page, programId)
                .map(commentEntity -> modelMapper.map(commentEntity, Comment.class));
    }
}
