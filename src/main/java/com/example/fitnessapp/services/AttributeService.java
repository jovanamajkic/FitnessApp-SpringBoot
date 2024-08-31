package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dto.Attribute;

import java.util.List;

public interface AttributeService {
    List<Attribute> findAllByCategory(Integer categoryId);
}
