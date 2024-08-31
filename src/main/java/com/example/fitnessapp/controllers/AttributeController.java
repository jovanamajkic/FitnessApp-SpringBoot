package com.example.fitnessapp.controllers;

import com.example.fitnessapp.models.dto.Attribute;
import com.example.fitnessapp.models.dto.AttributeValue;
import com.example.fitnessapp.services.AttributeService;
import com.example.fitnessapp.services.AttributeValueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/attributes")
public class AttributeController {
    private final AttributeService attributeService;
    private final AttributeValueService valueService;

    public AttributeController(AttributeService attributeService, AttributeValueService valueService) {
        this.attributeService = attributeService;
        this.valueService = valueService;
    }

    @GetMapping("/bycategory/{categoryId}")
    public List<Attribute> getAttributes(@PathVariable Integer categoryId){
        return attributeService.findAllByCategory(categoryId);
    }

    @GetMapping("/{id}/values")
    public List<AttributeValue> getValues(@PathVariable Integer id){
        return valueService.findAllByAttribute(id);
    }
}
