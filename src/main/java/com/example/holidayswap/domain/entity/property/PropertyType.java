package com.example.holidayswap.domain.entity.property;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "property_type", schema = "public")
public class PropertyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_type_id", nullable = false)
    private Long id;

    @Column(name = "property_type_description", length = Integer.MAX_VALUE)
    private String propertyTypeDescription;
    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "property_type_name", length = Integer.MAX_VALUE)
    private String propertyTypeName;
}