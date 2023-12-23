package com.example.holidayswap.domain.dto.response.property.timeFrame;

import com.example.holidayswap.domain.dto.response.booking.TimeHasBooked;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTimeStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AvailableTimeResponse {
    private Long id;
    private LocalDate startTime;
    private LocalDate endTime;
    private double pricePerNight;
    private boolean isDeleted;
    private AvailableTimeStatus status;
    private Long timeFrameId;
    private List<TimeHasBooked> timeHasBooked;
}
