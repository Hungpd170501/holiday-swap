package com.example.holidayswap.domain.dto.response.resort.amenity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResortAmenityResponse {
    private Long id;
    private String resortAmenityName;
    private String resortAmenityDescription;
    private String resortAmenityLinkIcon;
    private Boolean isDeleted;
    private Long resortAmenityTypeId;
}
