package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.dto.response.property.inRoomAmenity.InRoomAmenityTypeResponse;
import lombok.Data;

import java.util.List;

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
    private List<PropertyContractResponse> propertyContracts;
    private List<PropertyImageResponse> propertyImages;
    //    private Set<Facility> facilities = new LinkedHashSet<>();
    private List<InRoomAmenityTypeResponse> inRoomAmenityTypes;
}
