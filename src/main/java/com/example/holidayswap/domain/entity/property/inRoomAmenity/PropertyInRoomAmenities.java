package com.example.holidayswap.domain.entity.property.inRoomAmenity;

import com.example.holidayswap.domain.entity.property.Property;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "property_in_room_amenity", schema = "public")
public class PropertyInRoomAmenities {
    @EmbeddedId
    private PropertyInRoomAmenitiesTypeId id;

    @MapsId("inRoomAmenityId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "in_room_amenity_id", nullable = false)
    private InRoomAmenity inRoomAmenity;

    @MapsId("propertyId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

}