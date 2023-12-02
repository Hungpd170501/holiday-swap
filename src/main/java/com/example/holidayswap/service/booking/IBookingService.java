package com.example.holidayswap.service.booking;


import com.example.holidayswap.domain.dto.request.booking.BookingRequest;
import com.example.holidayswap.domain.dto.response.booking.HistoryBookingDetailResponse;
import com.example.holidayswap.domain.dto.response.booking.HistoryBookingResponse;
import com.example.holidayswap.domain.dto.response.booking.HistoryDetailBookingOwnerResponse;
import com.example.holidayswap.domain.dto.response.booking.TimeHasBooked;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;

import java.time.LocalDate;
import java.util.List;

public interface IBookingService {
    EnumBookingStatus.BookingStatus createBooking(BookingRequest bookingRequest) throws InterruptedException;

    List<HistoryBookingResponse> historyBookingUserLogin();

    HistoryBookingDetailResponse historyBookingDetail(Long bookingId);

    List<HistoryBookingResponse> historyBookingOwnerLogin();

    HistoryDetailBookingOwnerResponse historyBookingDetailOwner(Long bookingId);

    List<TimeHasBooked> getTimeHasBooked(Long timeFrameId, int year);

    void deactiveResortNotifyBookingUser(Long resortId, LocalDate startDate);

    void deactivePropertyNotifyBookingUser(Long propertyId, LocalDate startDate);

    String returnPointBooking(Long bookingId) throws InterruptedException;

    void refundPointBookingToOwner(LocalDate date);
}
