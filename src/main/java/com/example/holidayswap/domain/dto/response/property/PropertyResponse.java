package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.dto.response.property.amenity.InRoomAmenityTypeResponse;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
import lombok.Data;

import java.util.List;

@Data
public class PropertyResponse {
    private Long id;
    private String propertyName;
    private String propertyDescription;
    private int numberKingBeds;
    private int numberQueenBeds;
    private int numberSingleBeds;
    private int numberDoubleBeds;
    private int numberTwinBeds;
    private int numberFullBeds;
    private int numberSofaBeds;
    private int numberMurphyBeds;
    private int numberBedsRoom;
    private int numberBathRoom;
    private double roomSize;
    private Boolean isDeleted;
    private PropertyStatus status;
    private Long resortId;
    private PropertyTypeResponse propertyType;
    private PropertyViewResponse propertyView;
    private List<InRoomAmenityTypeResponse> inRoomAmenityType;
    private List<PropertyImageResponse> propertyImage;
    private Double rating;
}
