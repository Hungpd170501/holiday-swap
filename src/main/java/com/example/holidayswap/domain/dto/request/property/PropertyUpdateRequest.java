package com.example.holidayswap.domain.dto.request.property;

import com.example.holidayswap.domain.entity.property.PropertyImage;
import com.example.holidayswap.domain.entity.property.inRoomAmenity.InRoomAmenity;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class PropertyUpdateRequest {
    private Long propertyTypeId;
    private Long resortId;
    private int kingBeds;
    private int qeenBeds;
    private int twinBeds;
    private int fullBeds;
    private int sofaBeds;
    private int murphyBeds;
    private int numberBedsRoom;
    private int numberBathRoom;
    private int viewId;
    //    private PropertyContract propertyContracts;
    private Set<PropertyImage> propertyImages = new LinkedHashSet<>();
    private Set<InRoomAmenity> facilities = new LinkedHashSet<>();
}
