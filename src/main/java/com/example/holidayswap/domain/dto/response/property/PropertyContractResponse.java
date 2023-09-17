package com.example.holidayswap.domain.dto.response.property;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class PropertyContractResponse {
    private Long id;
    private OffsetDateTime endPeriod;
    private OffsetDateTime endTime;
    private String imgContract;
    private OffsetDateTime startTime;
    private boolean isDeleted;
    private Long propertyId;
}
