package com.example.holidayswap.domain.dto.response.resort;

import com.example.holidayswap.domain.dto.response.property.PropertyTypeResponse;
import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityTypeResponse;
import lombok.Data;

import java.util.List;

@Data
public class ResortResponse {
    private Long id;
    private String resortName;
    private String resortDescription;
    private Long addressId;
    private boolean isDeleted;
    private List<ResortImageResponse> resortImages;
    private List<PropertyTypeResponse> propertyTypes;
    private List<ResortAmenityTypeResponse> resortAmenityTypes;
}
