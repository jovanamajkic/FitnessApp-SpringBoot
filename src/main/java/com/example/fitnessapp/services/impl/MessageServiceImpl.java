package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.exceptions.NotFoundException;
import com.example.fitnessapp.models.dto.JwtUser;
import com.example.fitnessapp.models.dto.Message;
import com.example.fitnessapp.models.entities.MessageEntity;
import com.example.fitnessapp.models.entities.UserEntity;
import com.example.fitnessapp.models.requests.MessageRequest;
import com.example.fitnessapp.repositories.MessageEntityRepository;
import com.example.fitnessapp.repositories.UserEntityRepository;
import com.example.fitnessapp.services.MessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    private final MessageEntityRepository repository;
    private final UserEntityRepository userRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LogManager.getLogger(MessageServiceImpl.class);

    public MessageServiceImpl(MessageEntityRepository repository, UserEntityRepository userRepository, ModelMapper modelMapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Message insert(MessageRequest request, Authentication auth) {
        MessageEntity messageEntity = modelMapper.map(request, MessageEntity.class);
        UserEntity userEntity = userRepository.findById(request.getUserId()).orElseThrow(NotFoundException::new);
        messageEntity.setUser(userEntity);
        messageEntity.setIsRead(false);
        messageEntity.setDate(LocalDate.now());
        if(request.getToUserId() == null){
            messageEntity.setToUserId(null);
        }

        repository.saveAndFlush(messageEntity);

        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        logger.info("Message " + messageEntity.getId() + " insert by user " + jwtUser.getId());

        return modelMapper.map(messageEntity, Message.class);
    }

    @Override
    public List<Message> findAllToUser(Integer toUserId, Authentication auth) {
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        logger.info("Find all messages to user " + toUserId);

        return repository.findAllByToUserId(toUserId)
                .stream()
                .map(entity -> modelMapper.map(entity, Message.class))
                .sorted(Comparator.comparing(Message::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void updateIsRead(Integer id, Authentication auth) {
        MessageEntity entity = repository.findById(id).orElseThrow(NotFoundException::new);
        entity.setIsRead(true);
        repository.saveAndFlush(entity);
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        logger.info("Message " + entity.getId() + " update isRead by user " + jwtUser.getId());
    }
}
