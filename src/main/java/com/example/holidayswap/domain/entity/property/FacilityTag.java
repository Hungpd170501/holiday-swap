package com.example.holidayswap.domain.entity.property;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "facility_tag", schema = "public")
public class FacilityTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_tag_id", nullable = false)
    private Long id;

    @Column(name = "facility_tag_name", length = Integer.MAX_VALUE)
    private String facilityTagName;

    @OneToMany(mappedBy = "facilityTag")
    private Set<Facility> facilities = new LinkedHashSet<>();

}