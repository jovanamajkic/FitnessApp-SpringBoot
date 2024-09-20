package com.example.fitnessapp.controllers;

import com.example.fitnessapp.models.dto.Program;
import com.example.fitnessapp.models.dto.UserHasProgram;
import com.example.fitnessapp.models.requests.UserHasProgramRequest;
import com.example.fitnessapp.services.UserHasProgramService;
import jakarta.validation.Valid;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user_has_programs")
public class UserHasProgramController {
    private final UserHasProgramService service;

    public UserHasProgramController(UserHasProgramService service) {
        this.service = service;
    }

    @PostMapping()
    public UserHasProgram insert(@RequestBody @Valid UserHasProgramRequest request, Authentication auth){
        return service.insert(request, auth);
    }

    @GetMapping("/user/{userId}")
    public List<Program> getByUser(@PathVariable Integer userId, Authentication auth){
        return service.getByUser(userId, auth);
    }

    @GetMapping("/{userId}/{programId}")
    public UserHasProgram get(@PathVariable Integer userId, @PathVariable Integer programId, Authentication auth){
        return service.getUserHasProgram(userId, programId, auth);
    }

    @Scheduled(cron = "0 6 16 * * ?")
    public void updateIsCompleted(){
        service.updateIsCompleted();
    }
}
