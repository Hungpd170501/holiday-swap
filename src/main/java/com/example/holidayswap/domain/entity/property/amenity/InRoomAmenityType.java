package com.example.holidayswap.domain.entity.property.amenity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    @Column(name = "in_room_amenity_type_description", length = Integer.MAX_VALUE)
    private String inRoomAmenityTypeDescription;
    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;
    @OneToMany(mappedBy = "inRoomAmenityType", fetch = FetchType.LAZY)
    private List<InRoomAmenity> inRoomAmenities;

}