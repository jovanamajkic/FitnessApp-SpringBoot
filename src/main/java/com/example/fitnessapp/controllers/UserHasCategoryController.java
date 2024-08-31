package com.example.fitnessapp.controllers;

import com.example.fitnessapp.models.dto.UserHasCategory;
import com.example.fitnessapp.models.requests.UserHasCategoryRequest;
import com.example.fitnessapp.services.UserHasCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user_has_category")
public class UserHasCategoryController {
    private final UserHasCategoryService service;

    public UserHasCategoryController(UserHasCategoryService service) {
        this.service = service;
    }

    @PostMapping
    public UserHasCategory subscribe(@RequestBody @Valid UserHasCategoryRequest request, Authentication auth){
        return service.insert(request, auth);
    }

    @DeleteMapping("/{userId}/{categoryId}")
    public ResponseEntity<Void> unsubscribe(@PathVariable Integer userId, @PathVariable Integer categoryId, Authentication auth){
        try {
            service.delete(userId, categoryId, auth);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("exists/{userId}/{categoryId}")
    public boolean exists(@PathVariable Integer userId, @PathVariable Integer categoryId, Authentication auth){
        return service.userHasCategoryExists(userId, categoryId, auth);
    }

    @Scheduled(cron = "0 17 17 * * ?")
    public void sendMail(){
        service.sendMail();
    }
}
