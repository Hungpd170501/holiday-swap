package com.example.holidayswap.domain.dto.request.resort;

import lombok.Data;

import java.util.List;

@Data
public class ResortRequest {
    private String resortName;
    private String resortDescription;
    private Long locationId;
    private List<Long> amenities;
    private List<Long> propertyTypes;
}
