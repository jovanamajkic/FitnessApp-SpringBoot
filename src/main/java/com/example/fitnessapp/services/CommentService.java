package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dto.Comment;
import com.example.fitnessapp.models.requests.CommentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface CommentService {
    Comment insert(CommentRequest request, Authentication auth);
    Page<Comment> findAllByProgram(Pageable page, Integer programId);
}
