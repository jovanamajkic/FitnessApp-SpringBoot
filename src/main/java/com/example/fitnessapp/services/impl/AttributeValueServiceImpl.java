package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.models.dto.AttributeValue;
import com.example.fitnessapp.repositories.AttributeValueEntityRepository;
import com.example.fitnessapp.services.AttributeValueService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttributeValueServiceImpl implements AttributeValueService {
    private final AttributeValueEntityRepository repository;
    private final ModelMapper modelMapper;

    public AttributeValueServiceImpl(AttributeValueEntityRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AttributeValue> findAllByAttribute(Integer attributeId) {
        return repository.findAllByAttribute_Id(attributeId)
                .stream()
                .map(attributeValueEntity -> modelMapper.map(attributeValueEntity, AttributeValue.class))
                .collect(Collectors.toList());
    }
}
