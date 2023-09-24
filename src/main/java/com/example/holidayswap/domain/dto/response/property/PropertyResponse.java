package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.dto.response.property.inRoomAmenity.InRoomAmenityTypeResponse;
import lombok.Data;

import java.util.List;

@Data
public class PropertyResponse {
    private Long id;
    private Long userId;
    private Long resortId;
    private Long propertyTypeId;
    private String status;
    private Boolean isDeleted;
    private Long viewId;
    //    private PropertyType propertyType;
    //    private Resort resort;
    //    private User user;
    private int kingBeds;
    private int queenBeds;
    private int twinBeds;
    private int fullBeds;
    private int sofaBeds;
    private int murphyBeds;
    private List<PropertyContractResponse> propertyContracts;
    private List<PropertyImageResponse> propertyImages;
    private List<InRoomAmenityTypeResponse> inRoomAmenityTypes;
}
