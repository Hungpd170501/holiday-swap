package com.example.holidayswap.domain.dto.request.resort.amenity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResortAmenityRequest {
    private String resortAmenityName;
    private String resortAmenityDescription;
    private Long resortAmenityTypeId;
}
