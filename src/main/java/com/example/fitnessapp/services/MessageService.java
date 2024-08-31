package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dto.Message;
import com.example.fitnessapp.models.requests.MessageRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface MessageService {
    Message insert(MessageRequest request, Authentication auth);
    List<Message> findAllToUser(Integer toUserId, Authentication auth);
    void updateIsRead(Integer id, Authentication auth);
}
