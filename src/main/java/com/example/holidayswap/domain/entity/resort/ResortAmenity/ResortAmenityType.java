package com.example.holidayswap.domain.entity.resort.ResortAmenity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "resort_amenity_type", schema = "public")
public class ResortAmenityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resort_amenity_type_id", nullable = false)
    private Long id;

    @Column(name = "resort_amenity_type_name", length = Integer.MAX_VALUE)
    private String inRoomAmenityTypeName;
    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "resortAmenityType", fetch = FetchType.LAZY)
    private List<ResortAmenity> resortAmenities;

}