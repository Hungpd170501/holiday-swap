package com.example.holidayswap.domain.dto.request.property.timeFrame;

import lombok.Data;

import java.util.Date;

@Data
public class TimeFrameRequest {
    private Date startTime;
    private Date endTime;
}
