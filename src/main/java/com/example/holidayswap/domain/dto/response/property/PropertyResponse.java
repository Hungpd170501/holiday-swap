package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.entity.property.PropertyContract;
import com.example.holidayswap.domain.entity.property.PropertyImage;
import com.example.holidayswap.domain.entity.property.inRoomAmenity.InRoomAmenityType;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class PropertyResponse {
    private Long id;
    private Boolean isDeleted;
    private String status;
    private Long propertyTypeId;
    //    private PropertyType propertyType;
    private Long resortId;
    //    private Resort resort;
    private Long userId;
    //    private User user;
    private int kingBeds;
    private int qeenBeds;
    private int twinBeds;
    private int fullBeds;
    private int sofaBeds;
    private int murphyBeds;
    private int viewId;
    private Set<PropertyContract> propertyContracts = new LinkedHashSet<>();
    private Set<PropertyImage> propertyImages = new LinkedHashSet<>();
    //    private Set<Facility> facilities = new LinkedHashSet<>();
    private Set<InRoomAmenityType> inRoomAmenityTypes = new LinkedHashSet<>();
}
