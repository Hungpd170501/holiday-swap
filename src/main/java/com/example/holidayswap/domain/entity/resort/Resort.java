package com.example.holidayswap.domain.entity.resort;

import com.example.holidayswap.domain.entity.address.Location;
import com.example.holidayswap.domain.entity.property.PropertyType;
import com.example.holidayswap.domain.entity.resort.amentity.ResortAmenity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "resort")
public class Resort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resort_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "resort_name")
    private String resortName;

    @Column(name = "location_id")
    private Long locationId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "location_id",
            referencedColumnName = "location_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private Location location;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    @OneToMany(mappedBy = "resort")
    private List<ResortImage> resortImages;

    @ManyToMany
    @JoinTable(
            name = "resorts_amenities",
            joinColumns = @JoinColumn(name = "resort_id"),
            inverseJoinColumns = @JoinColumn(name = "resort_amenity_id"))
    private List<ResortAmenity> amenities;
    @ManyToMany
    @JoinTable(
            name = "resorts_property_type_property",
            joinColumns = @JoinColumn(name = "resort_id"),
            inverseJoinColumns = @JoinColumn(name = "property_type_id"))
    private List<PropertyType> propertyTypes;

}