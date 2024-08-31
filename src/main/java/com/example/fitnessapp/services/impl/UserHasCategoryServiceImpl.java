package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.exceptions.NotFoundException;
import com.example.fitnessapp.models.dto.JwtUser;
import com.example.fitnessapp.models.dto.UserHasCategory;
import com.example.fitnessapp.models.entities.CategoryEntity;
import com.example.fitnessapp.models.entities.UserEntity;
import com.example.fitnessapp.models.entities.UserHasCategoryEntity;
import com.example.fitnessapp.models.requests.UserHasCategoryRequest;
import com.example.fitnessapp.repositories.CategoryEntityRepository;
import com.example.fitnessapp.repositories.ProgramEntityRepository;
import com.example.fitnessapp.repositories.UserEntityRepository;
import com.example.fitnessapp.repositories.UserHasCategoryEntityRepository;
import com.example.fitnessapp.services.UserHasCategoryService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserHasCategoryServiceImpl implements UserHasCategoryService {
    private final UserHasCategoryEntityRepository repository;
    private final UserEntityRepository userRepository;
    private final CategoryEntityRepository categoryRepository;
    private final ProgramEntityRepository programRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LogManager.getLogger(UserHasCategoryServiceImpl.class);
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromMail;

    public UserHasCategoryServiceImpl(UserHasCategoryEntityRepository repository, UserEntityRepository userRepository, CategoryEntityRepository categoryRepository, ProgramEntityRepository programRepository, ModelMapper modelMapper, JavaMailSender mailSender) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.programRepository = programRepository;
        this.modelMapper = modelMapper;
        this.mailSender = mailSender;
    }

    @Override
    public UserHasCategory insert(UserHasCategoryRequest request, Authentication auth) {
        UserEntity user = userRepository.findById(request.getUserId()).orElseThrow(NotFoundException::new);
        CategoryEntity category = categoryRepository.findById(request.getCategoryId()).orElseThrow(NotFoundException::new);
        UserHasCategoryEntity entity = modelMapper.map(request, UserHasCategoryEntity.class);
        entity.setUser(user);
        entity.setCategory(category);
        repository.saveAndFlush(entity);
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        logger.info("UserHasCategory " + entity.getId() + " insert by user " + jwtUser.getId());
        return modelMapper.map(entity, UserHasCategory.class);
    }

    @Override
    public void delete(Integer userId, Integer categoryId, Authentication auth) {
        UserHasCategoryEntity entity = repository.findByUser_IdAndCategory_Id(userId, categoryId);
        repository.delete(entity);
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        logger.info("UserHasCategory " + entity.getId() + " delete by user " + jwtUser.getId());
    }

    @Override
    public boolean userHasCategoryExists(Integer userId, Integer categoryId, Authentication auth) {
        return repository.existsByUser_IdAndCategory_Id(userId, categoryId);
    }

    @Override
    public void sendMail() {
        repository.findAll()
                .forEach(entity -> {
                    String userEmail = entity.getUser().getEmail();
                    StringBuilder programs = new StringBuilder();
                    programRepository.findAllByCategory_Id(entity.getCategory().getId())
                            .forEach(program ->
                                programs.append(program.getTitle())
                                        .append(": ")
                                        .append(program.getDescription())
                                        .append("\n")
                                        .append("Duration: ")
                                        .append(program.getDuration())
                                        .append(", Price: ")
                                        .append(program.getPrice())
                                        .append("\n")
                            );
                    String content = "This is your daily subscription mail. Current available programs for " + entity.getCategory().getName() + " category are:\n"
                            + programs;
                    sendMail(userEmail, content);
                });
    }

    private void sendMail(String mail, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(mail);
        message.setSubject("Category subscription");
        message.setText(content);
        mailSender.send(message);
    }
}
