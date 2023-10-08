package com.example.holidayswap.domain.dto.request.property;

import lombok.Data;

@Data
public class PropertyUpdateRequest {
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
    private Long resortId;
    private Long propertyTypeId;
    private Long propertyViewId;
//    private List<Long> inRoomAmenities;
//    private List<InRoomAmenity> inRoomAmenities;
}
