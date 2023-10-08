package com.example.holidayswap.domain.dto.response.property.amenity;

import lombok.Data;

@Data
public class InRoomAmenityResponse {
    private Long id;
    private String inRoomAmenityName;
    private String inRoomAmenityDescription;
    private String inRoomAmenityLinkIcon;
    private Boolean isDeleted;
    private Long inRoomAmenityTypeId;
}
