package com.example.holidayswap.domain.dto.request.resort.amenity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResortAmenityRequest {
    private String resortAmenityName;
    private String resortAmenityDescription;
    private Long resortAmenityTypeId;
}
