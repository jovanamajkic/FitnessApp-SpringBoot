package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.models.dto.Category;
import com.example.fitnessapp.repositories.CategoryEntityRepository;
import com.example.fitnessapp.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryEntityRepository repository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryEntityRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll().stream()
                .map(categoryEntity -> modelMapper.map(categoryEntity, Category.class))
                .collect(Collectors.toList());
    }
}
