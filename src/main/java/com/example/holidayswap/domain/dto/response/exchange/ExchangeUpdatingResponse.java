package com.example.holidayswap.domain.dto.response.exchange;

import com.example.holidayswap.domain.dto.request.booking.UserOfBookingRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeUpdatingResponse {
    private Long availableTimeId;
    private Long userId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int totalMember;
    private List<UserOfBookingRequest> guestList;
}
