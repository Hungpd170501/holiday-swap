package com.example.holidayswap.service.booking;

import com.example.holidayswap.domain.dto.request.booking.UserOfBookingRequest;

import java.util.List;

public interface IUserOfBookingService {
    void saveUserOfBooking(Long bookingId, List<UserOfBookingRequest> userOfBookingRequests);
}
