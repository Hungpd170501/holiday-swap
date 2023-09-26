package com.example.holidayswap.domain.dto.response.resort.amenity;

import lombok.Data;

import java.util.List;

@Data
public class ResortAmenityTypeResponse {
    private Long id;
    private String resortAmenityTypeName;
    private String resortAmenityTypeDescription;
    private Boolean isDeleted;
    private List<ResortAmenityResponse> resortAmenities;
}
