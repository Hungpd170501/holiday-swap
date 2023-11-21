package com.example.holidayswap.domain.dto.request.resort;

import com.example.holidayswap.domain.dto.request.address.LocationRequest;
import lombok.Data;

import java.util.List;

@Data
public class ResortUpdateRequest {
    private String resortName;
    private String resortDescription;
    private List<Long> amenities;
    private List<Long> propertyTypes;
    private List<String> oldImages;
    private LocationRequest location;
}
