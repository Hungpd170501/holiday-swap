package com.example.holidayswap.domain.dto.request.property;

import com.example.holidayswap.domain.dto.request.property.vacation.VacationRequest;
import lombok.Data;

import java.util.List;

@Data
public class PropertyRegisterRequest {
    private Long propertyTypeId;
    private Long resortId;
    //    private Long userId;
    private Long viewId;
    private int kingBeds;
    private int queenBeds;
    private int twinBeds;
    private int fullBeds;
    private int sofaBeds;
    private int murphyBeds;
    private int numberBedsRoom;
    private int numberBathRoom;
    private List<VacationRequest> vacation;
    private PropertyContractRequest propertyContractRequest;
//    private List<PropertyInRoomAmenityRequest> propertyInRoomAmenityRequests;
}
