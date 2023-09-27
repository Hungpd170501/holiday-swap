package com.example.holidayswap.domain.dto.request.property;

import com.example.holidayswap.domain.dto.request.property.ownership.OwnershipRequest;
import lombok.Data;

import java.util.List;

@Data
public class PropertyRegisterRequest {
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
    private Long propertyViewId;
    private List<Long> inRoomAmenities;
    private OwnershipRequest ownershipRequest;
}
