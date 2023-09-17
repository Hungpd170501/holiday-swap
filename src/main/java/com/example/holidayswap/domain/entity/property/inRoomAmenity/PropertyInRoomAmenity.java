package com.example.holidayswap.domain.entity.property.inRoomAmenity;

import com.example.holidayswap.domain.entity.property.Property;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "property_in_room_amenity", schema = "public")
public class PropertyInRoomAmenity {
    @EmbeddedId
    private PropertyInRoomAmenityId id;
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private Boolean isDeleted;
    @MapsId("propertyId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;
    @MapsId("inRoomAmenityId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "in_room_amenity_id", nullable = false)
    private InRoomAmenity inRoomAmenity;
}