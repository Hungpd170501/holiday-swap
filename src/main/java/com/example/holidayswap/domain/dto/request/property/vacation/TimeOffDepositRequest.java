package com.example.holidayswap.domain.dto.request.property.vacation;

import lombok.Data;

import java.util.Date;

@Data
public class TimeOffDepositRequest {
    private Date startTime;
    private Date endTime;
    private double pricePerNight;
    private double numberNight;
    private String status;
    private Long vacationId;
}
