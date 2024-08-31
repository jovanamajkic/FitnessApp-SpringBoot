package com.example.fitnessapp.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "program_has_attribute_value")
public class ProgramHasAttributeValueEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", referencedColumnName = "id", nullable = false)
    private ProgramEntity program;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AttributeValue_id", referencedColumnName = "id", nullable = false)
    private AttributeValueEntity attributeValue;

}
