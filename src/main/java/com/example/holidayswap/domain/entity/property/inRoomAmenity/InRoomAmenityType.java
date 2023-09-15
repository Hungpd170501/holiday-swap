package com.example.holidayswap.domain.entity.property.inRoomAmenity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "in_room_amenity_type", schema = "public")
public class InRoomAmenityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "in_room_amenity_type_id", nullable = false)
    private Long id;

    @Column(name = "in_room_amenity_type_name", length = Integer.MAX_VALUE)
    private String inRoomAmenityTypeName;
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToMany(mappedBy = "inRoomAmenityType")
    private Set<InRoomAmenity> inRoomAmenities = new LinkedHashSet<>();

}