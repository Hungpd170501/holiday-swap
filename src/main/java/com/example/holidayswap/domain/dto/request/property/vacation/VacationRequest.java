package com.example.holidayswap.domain.dto.request.property.vacation;

import lombok.Data;

import java.util.Date;

@Data
public class VacationRequest {
    private Date startTime;
    private Date endTime;
    private Long propertyId;
}
