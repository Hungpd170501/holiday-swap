package com.example.holidayswap.domain.dto.request.property;

import com.example.holidayswap.domain.dto.request.property.inRoomAmenity.PropertyInRoomAmenityRequest;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class PropertyUpdateRequest {
    private Long propertyTypeId;
    private Long resortId;
    private int viewId;
    private int kingBeds;
    private int qeenBeds;
    private int twinBeds;
    private int fullBeds;
    private int sofaBeds;
    private int murphyBeds;
    private int numberBedsRoom;
    private int numberBathRoom;
    private PropertyContractRequest propertyContractRequest;
    private Set<PropertyImageRequest> propertyImageRequests = new LinkedHashSet<>();
    private Set<PropertyInRoomAmenityRequest> propertyInRoomAmenityRequests = new LinkedHashSet<>();
}
