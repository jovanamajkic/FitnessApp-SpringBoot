package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dto.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
}
