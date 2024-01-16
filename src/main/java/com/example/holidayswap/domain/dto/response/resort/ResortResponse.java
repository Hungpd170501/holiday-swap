package com.example.holidayswap.domain.dto.response.resort;

import com.example.holidayswap.domain.dto.response.property.PropertyTypeResponse;
import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityTypeResponse;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ResortResponse {
    private Long id;
    private String resortName;
    private String resortDescription;
    private ResortStatus status;
    private boolean isDeleted;
    private List<ResortImageResponse> resortImages;
    private List<PropertyTypeResponse> propertyTypes;
    private List<ResortAmenityTypeResponse> resortAmenityTypes;
    private String addressLine;
    private String locationFormattedName;
    private String locationDescription;
    private String locationCode;
    private String postalCode;
    private Float latitude;
    private Float longitude;
    private Set<ResortMaintanceResponse> resortMaintainces;
}
