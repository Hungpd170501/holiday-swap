package com.example.holidayswap.domain.dto.request.property.amenity;

import lombok.Data;

@Data
public class InRoomAmenityRequest {
    private String inRoomAmenityName;

    private String inRoomAmenityDescription;

    private Long inRoomAmenityTypeId;

}
