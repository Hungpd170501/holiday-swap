package com.example.holidayswap.domain.dto.request.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private Long availableTimeId;
    private Long userId;
    private Date checkInDate;
    private Date checkOutDate;
    private int numberOfGuest;
    private List<UserOfBookingRequest> userOfBookingRequests;
}
