package com.example.holidayswap.domain.dto.response.resort.amenity;

import lombok.Data;

@Data
public class ResortAmenityResponse {
    private Long id;
    private String resortAmenityName;
    private String resortAmenityDescription;
    private Boolean isDeleted;
    private Long resortAmenityTypeId;
}
