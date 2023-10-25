package com.example.holidayswap.domain.dto.response.resort;

import com.example.holidayswap.domain.dto.response.property.PropertyTypeResponse;
import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityTypeResponse;
import com.example.holidayswap.domain.entity.address.District;
import lombok.Data;

import java.util.List;

@Data
public class ResortResponse {
    private Long id;
    private String resortName;
    private String resortDescription;
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
}
