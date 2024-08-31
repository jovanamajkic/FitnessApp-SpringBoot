package com.example.fitnessapp.controllers;

import com.example.fitnessapp.models.dto.Comment;
import com.example.fitnessapp.models.requests.CommentRequest;
import com.example.fitnessapp.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
public class CommentController {
    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@RequestBody @Valid CommentRequest request, Authentication auth){
        return service.insert(request, auth);
    }

    @GetMapping("by_program/{programId}")
    public Page<Comment> getByProgram(@PathVariable Integer programId, @RequestParam int page, @RequestParam int size){
        Pageable pageable = PageRequest.of(page, size);
        return service.findAllByProgram(pageable, programId);
    }
}
