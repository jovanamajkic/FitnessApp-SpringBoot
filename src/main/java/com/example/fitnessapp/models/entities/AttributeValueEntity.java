package com.example.fitnessapp.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "attribute_value")
public class AttributeValueEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "value", nullable = false)
    private String value;
    @ManyToOne
    @JoinColumn(name = "Attribute_id", referencedColumnName = "id", nullable = false)
    private AttributeEntity attribute;

}
