package com.example.holidayswap.domain.dto.response.property.timeFrame;

import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTimeStatus;
import lombok.Data;

import java.util.Date;

@Data
public class AvailableTimeResponse {
    private Long id;
    private Date startTime;
    private Date endTime;
    private double pricePerNight;
    private boolean isDeleted;
    private AvailableTimeStatus status;
    private Long timeFrameId;

}
