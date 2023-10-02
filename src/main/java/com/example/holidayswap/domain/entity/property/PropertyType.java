package com.example.holidayswap.domain.entity.property;

import com.example.holidayswap.domain.entity.resort.Resort;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    @ManyToMany
    @JoinTable(
            name = "resorts_property_type_property",
            joinColumns = @JoinColumn(name = "property_type_id"),
            inverseJoinColumns = @JoinColumn(name = "resort_id"))
    private List<Resort> resorts;
    @OneToMany
    private List<Property> properties;
}