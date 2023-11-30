package com.example.holidayswap.domain.entity.property.amenity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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
    @Length(min = 5, message = "Name length must greater than 5")
    @Column(name = "in_room_amenity_type_name", length = Integer.MAX_VALUE)
    @NotNull
    private String inRoomAmenityTypeName;
    @Column(name = "in_room_amenity_type_description", length = Integer.MAX_VALUE)
    private String inRoomAmenityTypeDescription;
    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    @NotNull
    private Boolean isDeleted = false;
    @OneToMany(mappedBy = "inRoomAmenityType", fetch = FetchType.LAZY)
    private List<InRoomAmenity> inRoomAmenities;

}