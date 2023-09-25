package com.example.holidayswap.domain.dto.response.resort;

import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityTypeResponse;
import lombok.Data;

import java.util.List;

@Data
public class ResortResponse {
    private Long id;
    private String resortName;
    private Long locationId;
    private boolean isDeleted;
    private List<ResortImageResponse> resortImages;
    private List<ResortAmenityTypeResponse> resortAmenityTypes;
}
