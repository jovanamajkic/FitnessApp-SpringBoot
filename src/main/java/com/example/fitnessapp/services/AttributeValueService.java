package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dto.AttributeValue;

import java.util.List;

public interface AttributeValueService {
    List<AttributeValue> findAllByAttribute(Integer attributeId);
}
