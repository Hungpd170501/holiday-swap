package com.example.holidayswap.domain.dto.response.property.timeFrame;

import com.example.holidayswap.domain.dto.response.booking.TimeHasBooked;
import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrameStatus;
import lombok.Data;

import java.util.List;

@Data
public class TimeFrameResponse {
    private Long id;
    private int weekNumber;
    private boolean isDeleted;
    private TimeFrameStatus status;
    private Long propertyId;
    private Long userId;
    private String roomId;
    private List<AvailableTimeResponse> availableTimes;
    private List<TimeHasBooked> timeHasBooked;
}
