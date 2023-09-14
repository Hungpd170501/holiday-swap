package com.example.holidayswap.domain.entity.property.facility;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "facility_type", schema = "public")
public class FacilityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_type_id", nullable = false)
    private Long id;

    @Column(name = "facility_name", length = Integer.MAX_VALUE)
    private String facilityName;

    @OneToMany(mappedBy = "facilityType")
    private Set<Facility> facilities = new LinkedHashSet<>();

}