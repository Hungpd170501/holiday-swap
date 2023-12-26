package com.example.holidayswap.domain.dto.request.property.timeFrame;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AvailableTimeRequest {
    private LocalDate startTime;
    private LocalDate endTime;
    private double pricePerNight;
}
