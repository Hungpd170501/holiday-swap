package com.example.holidayswap.domain.entity.property.facility;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "facility", schema = "public")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_id", nullable = false)
    private Long id;

    @Column(name = "facility_name", length = Integer.MAX_VALUE)
    private String facilityName;

    @Column(name = "facility_type_id")
    private Long facilityTypeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "facility_type_id",
            referencedColumnName = "facility_type_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private FacilityType facilityType;

}