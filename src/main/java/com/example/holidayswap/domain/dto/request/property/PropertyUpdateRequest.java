package com.example.holidayswap.domain.dto.request.property;

import com.example.holidayswap.domain.dto.request.property.ownership.OwnershipRequest;
import com.example.holidayswap.domain.entity.property.PropertyType;
import com.example.holidayswap.domain.entity.property.PropertyView;
import com.example.holidayswap.domain.entity.property.amenity.InRoomAmenity;
import lombok.Data;

import java.util.List;

@Data
public class PropertyUpdateRequest {
    private int numberKingBeds;
    private int numberQueensBeds;
    private int numberTwinBeds;
    private int numberFullBeds;
    private int numberSofaBeds;
    private int numberMurphyBeds;
    private int numberBedsRoom;
    private int numberBathRoom;
    private double roomSize;
    private Long propertyTypeId;
    private PropertyType propertyType;
    private Long propertyViewId;
    private PropertyView propertyView;
    private List<InRoomAmenity> inRoomAmenities;
    private OwnershipRequest propertyContractRequest;
    private List<PropertyImageRequest> propertyImageRequests;
}
