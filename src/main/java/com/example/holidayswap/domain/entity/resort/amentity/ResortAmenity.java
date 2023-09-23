package com.example.holidayswap.domain.entity.resort.amentity;

import com.example.holidayswap.domain.entity.resort.Resort;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "resort_amenity", schema = "public")
public class ResortAmenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resort_amenity_id", nullable = false)
    private Long id;
    @Column(name = "resort_amenity_name", length = Integer.MAX_VALUE)
    private String resortAmenityName;
    @Column(name = "resort_amenity_description", length = Integer.MAX_VALUE)
    private String resortAmenityDescription;
    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;
    @Column(name = "resort_amenity_type_id")
    private Long resortAmenityTypeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "resort_amenity_type_id",
            referencedColumnName = "resort_amenity_type_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private ResortAmenityType resortAmenityType;
    @ManyToMany
    private List<Resort> resorts;
}