package com.example.holidayswap.domain.dto.response.resort.amenity;

import lombok.Data;

@Data
public class ResortAmenityTypeResponse {
    private Long id;
    private String resortAmenityTypeName;
    private String resortAmenityTypeDescription;
    private Boolean isDeleted;
}
