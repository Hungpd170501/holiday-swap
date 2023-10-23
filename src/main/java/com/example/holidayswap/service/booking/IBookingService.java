package com.example.holidayswap.service.booking;


import com.example.holidayswap.domain.dto.request.booking.BookingRequest;
import com.example.holidayswap.domain.dto.response.booking.HistoryBookingDetailResponse;
import com.example.holidayswap.domain.dto.response.booking.HistoryBookingResponse;
import com.example.holidayswap.domain.dto.response.booking.HistoryDetailBookingOwnerResponse;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;

import java.util.List;

public interface IBookingService {
    EnumBookingStatus.BookingStatus createBooking(BookingRequest bookingRequest) throws InterruptedException;

    List<HistoryBookingResponse> historyBookingUserLogin();

    HistoryBookingDetailResponse historyBookingDetail(Long bookingId);

    List<HistoryBookingResponse> historyBookingOwnerLogin();

    HistoryDetailBookingOwnerResponse historyBookingDetailOwner(Long bookingId);
}
