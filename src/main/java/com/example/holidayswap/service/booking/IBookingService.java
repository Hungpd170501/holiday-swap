package com.example.holidayswap.service.booking;


import com.example.holidayswap.domain.dto.request.booking.BookingRequest;
import com.example.holidayswap.domain.dto.response.booking.HistoryBookingResponse;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;

import java.util.List;

public interface IBookingService {
    EnumBookingStatus.BookingStatus createBooking(BookingRequest bookingRequest) throws InterruptedException;

    List<HistoryBookingResponse> historyBookingUserLogin();
}
