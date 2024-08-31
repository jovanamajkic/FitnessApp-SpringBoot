package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.models.dto.Attribute;
import com.example.fitnessapp.repositories.AttributeEntityRepository;
import com.example.fitnessapp.services.AttributeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttributeServiceImpl implements AttributeService {
    private final AttributeEntityRepository repository;
    private final ModelMapper modelMapper;

    public AttributeServiceImpl(AttributeEntityRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Attribute> findAllByCategory(Integer categoryId) {
        return repository.findAllByCategory_Id(categoryId)
                .stream()
                .map(attributeEntity -> {
                    Attribute atributte = modelMapper.map(attributeEntity, Attribute.class);
                    atributte.setCategoryId(attributeEntity.getCategory().getId());
                    return atributte;
                })
                .collect(Collectors.toList());
    }
}
