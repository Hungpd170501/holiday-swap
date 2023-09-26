package com.example.holidayswap.domain.dto.response.property.amenity;

import lombok.Data;

import java.util.List;

@Data
public class InRoomAmenityTypeResponse {
    private Long id;
    private String inRoomAmenityTypeName;
    private String inRoomAmenityTypeDescription;
    private Boolean isDeleted;
    private List<InRoomAmenityResponse> inRoomAmenities;
}
