package com.example.holidayswap.domain.dto.response.exchange;

import com.example.holidayswap.domain.entity.exchange.ExchangeStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExchangeResponse {
    private Long exchangeId;
    private Long requestUserId;
    private Long requestAvailableTimeId;
    private LocalDate requestCheckInDate;
    private LocalDate requestCheckOutDate;
    private int requestTotalMember;
    private ExchangeStatus requestStatus;
    private Long userId;
    private Long availableTimeId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int totalMember;
    private ExchangeStatus status;
    private Long requestBookingId;
    private Long bookingId;
    private ExchangeStatus overallStatus;
}
