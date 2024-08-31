package com.example.fitnessapp.controllers;

import com.example.fitnessapp.models.dto.Program;
import com.example.fitnessapp.models.requests.ProgramRequest;
import com.example.fitnessapp.services.ProgramService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/programs")
public class ProgramController {
    private final ProgramService service;

    public ProgramController(ProgramService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Program getById(@PathVariable Integer id){
        return service.findById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Program> getByUser(@PathVariable Integer userId){
        return service.findAllByUser(userId);
    }

    @GetMapping
    public List<Program> getPrograms(){
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Program create(@RequestBody @Valid ProgramRequest request, Authentication auth){
        return service.insert(request, auth);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id, Authentication auth){
        service.delete(id, auth);
    }
}
