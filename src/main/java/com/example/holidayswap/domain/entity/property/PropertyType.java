package com.example.holidayswap.domain.entity.property;

import com.example.holidayswap.domain.entity.resort.Resort;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Column(name = "property_type_name", length = Integer.MAX_VALUE)
    private String propertyTypeName;
    @Column(name = "resort_id")
    private Long resortId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "resort_id",
            referencedColumnName = "resort_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private Resort resort;
}