package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.dto.response.property.inRoomAmenity.ContractImageResponse;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class PropertyContractResponse {
    private Long id;
    private OffsetDateTime endPeriod;
    private OffsetDateTime endTime;
    private OffsetDateTime startTime;
    private boolean isDeleted;
    private Long propertyId;
    private List<ContractImageResponse> contractImages;
}
