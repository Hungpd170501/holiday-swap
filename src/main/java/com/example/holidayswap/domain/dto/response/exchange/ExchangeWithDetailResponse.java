package com.example.holidayswap.domain.dto.response.exchange;

import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.dto.response.property.ApartmentForRentResponse;
import com.example.holidayswap.domain.entity.exchange.ExchangeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeWithDetailResponse {
    private Long exchangeId;
    private Long requestUserId;
    UserProfileResponse requestUser;
    private Long requestAvailableTimeId;
    private ApartmentForRentResponse requestAvailableTime;
    private LocalDate requestCheckInDate;
    private LocalDate requestCheckOutDate;
    private int requestTotalMember;
    private ExchangeStatus requestStatus;
    private Long userId;
    UserProfileResponse user;
    private Long availableTimeId;
    private ApartmentForRentResponse availableTime;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int totalMember;
    private ExchangeStatus status;
    private Long requestBookingId;
    private Long bookingId;
    private ExchangeStatus overallStatus;
}
