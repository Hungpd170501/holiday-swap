package com.example.holidayswap.domain.dto.response.property.vacation;

import com.example.holidayswap.domain.entity.property.vacation.TimeOffDepositStatus;
import com.example.holidayswap.domain.entity.property.vacation.TimeOffDepositType;
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
    private TimeOffDepositStatus status;
    private TimeOffDepositType type;
    private Long vacationId;
}
