package com.example.fitnessapp.controllers;

import com.example.fitnessapp.models.dto.Message;
import com.example.fitnessapp.models.requests.MessageRequest;
import com.example.fitnessapp.services.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("messages")
public class MessageController {
    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @PostMapping
    public Message insert(@RequestBody @Valid MessageRequest request, Authentication auth){
        return service.insert(request, auth);
    }

    @GetMapping("to_user/{toUserId}")
    public List<Message> getByToUser(@PathVariable Integer toUserId, Authentication auth){
        return service.findAllToUser(toUserId, auth);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateIsRead (@PathVariable Integer id, Authentication auth){
        try {
            service.updateIsRead(id, auth);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
