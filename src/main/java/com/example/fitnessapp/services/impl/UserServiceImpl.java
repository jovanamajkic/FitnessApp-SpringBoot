package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.exceptions.NotFoundException;
import com.example.fitnessapp.exceptions.UnauthorizedException;
import com.example.fitnessapp.models.dto.JwtUser;
import com.example.fitnessapp.models.dto.User;
import com.example.fitnessapp.models.entities.UserEntity;
import com.example.fitnessapp.models.enums.UserStatus;
import com.example.fitnessapp.models.requests.PasswordChangeRequest;
import com.example.fitnessapp.models.requests.UserUpdateRequest;
import com.example.fitnessapp.repositories.UserEntityRepository;
import com.example.fitnessapp.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserEntityRepository repository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserEntityRepository repository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity findById(Integer id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public User update(String username, UserUpdateRequest request, Authentication auth) {
        UserEntity user = repository.findByUsername(username).orElseThrow(NotFoundException::new);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setAvatar(request.getAvatar());
        user.setCity(request.getCity());
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        logger.info("User " + jwtUser.getId() + " update.");
        return modelMapper.map(repository.saveAndFlush(user), User.class);
    }

    @Override
    public List<User> findAll(Authentication auth) {
        return repository.findAll()
                .stream()
                .map(userEntity -> modelMapper.map(userEntity, User.class))
                .filter(user -> user.getStatus() == UserStatus.ACTIVE)
                .collect(Collectors.toList());
    }

    @Override
    public User changePassword(String username, PasswordChangeRequest request, Authentication auth) {
        UserEntity user = repository.findByUsername(username).orElseThrow(NotFoundException::new);
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        if(passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            logger.info("User " + jwtUser.getId() + " change password.");
            return modelMapper.map(repository.saveAndFlush(user), User.class);
        } else {
            logger.info("User " + jwtUser.getId() + " change password failed.");
            throw new UnauthorizedException("Current password is incorrect!");
        }
    }
}
