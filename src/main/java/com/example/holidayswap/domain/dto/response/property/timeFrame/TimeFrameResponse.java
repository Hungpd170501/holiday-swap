package com.example.holidayswap.domain.dto.response.property.timeFrame;

import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrameStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TimeFrameResponse {
    private Long id;
    private Date startTime;
    private Date endTime;
    private boolean isDeleted;
    private TimeFrameStatus status;
    private Long propertyId;
    private Long userId;
    private String roomId;
    private List<AvailableTimeResponse> availableTimes;
}
