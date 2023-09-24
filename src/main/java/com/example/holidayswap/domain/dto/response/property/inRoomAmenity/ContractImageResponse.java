package com.example.holidayswap.domain.dto.response.property.inRoomAmenity;

import lombok.Data;

@Data
public class ContractImageResponse {
    private Long id;
    private String link;
    private boolean isDeleted;
    private Long propertyContractId;
}
