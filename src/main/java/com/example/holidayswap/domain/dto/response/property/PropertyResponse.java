package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.property.*;
import com.example.holidayswap.domain.entity.property.facility.Facility;
import com.example.holidayswap.domain.entity.property.service.Service;
import com.example.holidayswap.domain.entity.resort.Resort;
import lombok.Data;

import java.util.Set;

@Data
public class PropertyResponse {
    private Long id;
    private User user;
    private Resort resort;
    private PropertyType propertyType;
    private PropertyStatus status;
    private Boolean isDeleted;
    private Set<PropertyAvailableTime> propertyAvailableTimes;
    private Set<PropertyContract> propertyContracts;
    private Set<Facility> facilities;
    private Set<ImageProperty> imageProperties;
    private Set<Service> services;
}
