package com.example.holidayswap.domain.dto.request.property;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class PropertyContractRequest {
    private OffsetDateTime endPeriod;
    private OffsetDateTime endTime;
    private OffsetDateTime startTime;
    private String imgContract;
}
