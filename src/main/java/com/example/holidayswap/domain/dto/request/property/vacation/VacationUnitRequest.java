package com.example.holidayswap.domain.dto.request.property.vacation;

import lombok.Data;

import java.util.Date;

@Data
public class VacationUnitRequest {
    private Date startTime;
    private Date endTime;
}
