package com.example.holidayswap.domain.dto.request.resort;

import com.example.holidayswap.domain.dto.request.address.LocationRequest;
import lombok.Data;

import java.util.List;

@Data
public class ResortRequest {
    private String resortName;
    private String resortDescription;
    private List<Long> amenities;
    private List<Long> propertyTypes;
    private LocationRequest location;
}
