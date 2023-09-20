package com.example.holidayswap.domain.dto.response.property.vacation;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class VacationResponse {
    private Long id;
    private Date startTime;
    private Date endTime;
    private boolean isDeleted;
    private String status;
    private Long propertyId;
    private List<TimeOffDepositResponse> timeOffDeposits;

}
