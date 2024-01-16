package com.example.holidayswap.domain.dto.request.exchange;

import com.example.holidayswap.domain.dto.request.booking.UserOfBookingRequest;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ExchangeUpdatingRequest {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int totalMember;
    private List<UserOfBookingRequest> guestList;
}
