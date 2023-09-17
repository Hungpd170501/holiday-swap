package com.example.holidayswap.domain.entity.property.inRoomAmenity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PropertyInRoomAmenityId implements Serializable {
    private static final long serialVersionUID = 6480270893354400353L;
    @NotNull
    @Column(name = "property_id", nullable = false)
    private Long propertyId;

    @NotNull
    @Column(name = "in_room_amenity_id", nullable = false)
    private Long inRoomAmenityId;
}