package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.exceptions.NotFoundException;
import com.example.fitnessapp.models.dto.AttributeValue;
import com.example.fitnessapp.models.dto.JwtUser;
import com.example.fitnessapp.models.dto.Program;
import com.example.fitnessapp.models.dto.ProgramHasAttributeValue;
import com.example.fitnessapp.models.entities.AttributeValueEntity;
import com.example.fitnessapp.models.entities.ProgramEntity;
import com.example.fitnessapp.models.entities.ProgramHasAttributeValueEntity;
import com.example.fitnessapp.models.requests.ProgramHasAttributeValueRequest;
import com.example.fitnessapp.repositories.AttributeValueEntityRepository;
import com.example.fitnessapp.repositories.ProgramEntityRepository;
import com.example.fitnessapp.repositories.ProgramHasAttributeValueEntityRepository;
import com.example.fitnessapp.services.ProgramHasAttributeValueService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgramHasAttributeValueServiceImpl implements ProgramHasAttributeValueService {
    private final ProgramHasAttributeValueEntityRepository repository;
    private final AttributeValueEntityRepository valueRepository;
    private final ProgramEntityRepository programRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LogManager.getLogger(ProgramHasAttributeValueServiceImpl.class);

    public ProgramHasAttributeValueServiceImpl(ProgramHasAttributeValueEntityRepository repository, AttributeValueEntityRepository valueRepository, ProgramEntityRepository programRepository, ModelMapper modelMapper) {
        this.repository = repository;
        this.valueRepository = valueRepository;
        this.programRepository = programRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProgramHasAttributeValue insert(ProgramHasAttributeValueRequest request, Authentication auth) {
        AttributeValueEntity attributeValue = valueRepository.findById(request.getAttributeValueId())
                .orElseThrow(NotFoundException::new);
        ProgramEntity program = programRepository.findById(request.getProgramId())
                .orElseThrow(NotFoundException::new);

        ProgramHasAttributeValueEntity entity = modelMapper.map(request, ProgramHasAttributeValueEntity.class);

        entity.setAttributeValue(attributeValue);
        entity.setProgram(program);
        repository.saveAndFlush(entity);

        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        logger.info("ProgramHasAttributeValue " + entity.getId() + " insert by user " + jwtUser.getId());

        return modelMapper.map(entity, ProgramHasAttributeValue.class);
    }

    @Override
    public List<Program> findProgramsByValue(Integer valueId) {
        List<ProgramEntity> programs = new ArrayList<>();
        repository.findAllByAttributeValue_Id(valueId)
                .forEach(element -> programs.add(element.getProgram()));

        return programs.stream()
                .map(entity -> modelMapper.map(entity, Program.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AttributeValue> findValuesByProgram(Integer programId) {
        List<AttributeValueEntity> values = new ArrayList<>();
        repository.findAllByProgram_Id(programId)
                .forEach(entity -> values.add(entity.getAttributeValue()));

        return values.stream()
                .map(entity -> modelMapper.map(entity, AttributeValue.class))
                .collect(Collectors.toList());
    }
}
