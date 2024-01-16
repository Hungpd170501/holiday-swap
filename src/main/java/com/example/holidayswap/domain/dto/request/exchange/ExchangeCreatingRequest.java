package com.example.holidayswap.domain.dto.request.exchange;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExchangeCreatingRequest {
    private Long requestAvailableTimeId;
    private LocalDate requestCheckInDate;
    private LocalDate requestCheckOutDate;
    private int requestTotalMember;
    private Long userId;
    private Long availableTimeId;
}
