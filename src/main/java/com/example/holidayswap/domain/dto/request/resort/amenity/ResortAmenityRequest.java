package com.example.holidayswap.domain.dto.request.resort.amenity;

import lombok.Data;

@Data
public class ResortAmenityRequest {
    private String resortAmenityName;
    private String resortAmenityDescription;
    private Long resortAmenityTypeId;
}
