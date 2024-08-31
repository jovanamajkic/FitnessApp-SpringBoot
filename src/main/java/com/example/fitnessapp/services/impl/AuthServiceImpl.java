package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.exceptions.ConflictException;
import com.example.fitnessapp.exceptions.NotFoundException;
import com.example.fitnessapp.exceptions.UnauthorizedException;
import com.example.fitnessapp.models.dto.LoginResponse;
import com.example.fitnessapp.models.dto.User;
import com.example.fitnessapp.models.entities.UserEntity;
import com.example.fitnessapp.models.enums.UserStatus;
import com.example.fitnessapp.models.requests.LoginRequest;
import com.example.fitnessapp.models.requests.RegistrationRequest;
import com.example.fitnessapp.repositories.UserEntityRepository;
import com.example.fitnessapp.services.AuthService;
import com.example.fitnessapp.util.LoggingUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserEntityRepository repository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private static final Logger logger = LogManager.getLogger(AuthServiceImpl.class);
    @Value("${authorization.token.expiration-time}")
    private String tokenExpirationTime;
    @Value("${authorization.token.secret}")
    private String tokenSecret;
    @Value("${spring.mail.username}")
    private String fromMail;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserEntityRepository repository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserEntity userEntity = repository.findByUsername(request.getUsername()).orElseThrow(NotFoundException::new);
            if(userEntity.getStatus().equals(UserStatus.ACTIVE)){
                LoginResponse response = modelMapper.map(userEntity, LoginResponse.class);
                response.setToken(generateJwt(userEntity));
                logger.info("User " + userEntity.getId() + " login.");
                return response;
            } else {
                sendActivationsEmail(userEntity);
                logger.info("Activation email sent to user " + userEntity.getId());
                return null;
            }
        } catch (NotFoundException e) {
            LoggingUtil.logException(e, getClass());
            throw new UnauthorizedException("Invalid username or password");
        } catch (UnauthorizedException e) {
            LoggingUtil.logException(e, getClass());
            throw e;
        } catch (Exception e){
            LoggingUtil.logException(e, getClass());
            throw new UnauthorizedException("Auth failed");
        }
    }

    private String generateJwt(UserEntity user) {
        return Jwts.builder()
                .setId(user.getId().toString())
                .claim("role", user.getStatus().name())
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(tokenExpirationTime)))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    @Override
    public void register(RegistrationRequest request) {
        if(repository.existsByUsername(request.getUsername()))
            throw new ConflictException();

        UserEntity userEntity = modelMapper.map(request, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setStatus(UserStatus.REQUESTED);
        userEntity.setVerificationCode(getActivationCode());
        repository.saveAndFlush(userEntity);

        sendActivationsEmail(userEntity);
        logger.info("User " + userEntity.getId() + " register.");
    }

    @Override
    public User activate(String code) {
        System.out.println("Activated code: " + code);
        UserEntity user = repository.findUserEntityByVerificationCode(code).orElseThrow(NotFoundException::new);
        System.out.println("Activated user: " + user);
        user.setStatus(UserStatus.ACTIVE);
        user.setVerificationCode(null);
        repository.saveAndFlush(user);
        logger.info("User " + user.getId() + " activate.");
        return modelMapper.map(user, User.class);
    }

    private void sendActivationsEmail(UserEntity user) {
        String subject = "Activate your account";
        String mailContent = "Dear " + user.getFirstName() + " " + user.getLastName() + ",\n";
        mailContent += "To activate your account, click the link below:\n"
                + "http://localhost:4200/auth/activate?code=" + user.getVerificationCode();
        mailContent += "\n\nThank you\nFitness App Team";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(mailContent);

        mailSender.send(message);
    }

    private String getActivationCode(){
        return UUID.randomUUID().toString();
    }
}
