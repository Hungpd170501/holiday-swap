package com.example.holidayswap.domain.dto.response.property.vacation;

import lombok.Data;

import java.util.Date;

@Data
public class TimeOffDepositResponse {
    private Long id;
    private Date startTime;
    private Date endTime;
    private double pricePerNight;
    private double numberNight;
    private boolean isDeleted;
    private String status;
    private Long vacationId;
}
