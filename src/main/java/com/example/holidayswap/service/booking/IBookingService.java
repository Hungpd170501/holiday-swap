package com.example.holidayswap.service.booking;


import com.example.holidayswap.domain.dto.request.booking.BookingRequest;
import com.example.holidayswap.domain.dto.response.booking.HistoryBookingDetailResponse;
import com.example.holidayswap.domain.dto.response.booking.HistoryBookingResponse;
import com.example.holidayswap.domain.dto.response.booking.HistoryDetailBookingOwnerResponse;
import com.example.holidayswap.domain.dto.response.booking.TimeHasBooked;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;

public interface IBookingService {
    EnumBookingStatus.BookingStatus createBooking(BookingRequest bookingRequest) throws InterruptedException, IOException, WriterException, MessagingException;

    List<HistoryBookingResponse> historyBookingUserLogin();

    HistoryBookingDetailResponse historyBookingDetail(Long bookingId);

    List<HistoryBookingResponse> historyBookingOwnerLogin();

    HistoryDetailBookingOwnerResponse historyBookingDetailOwner(Long bookingId);

    List<TimeHasBooked> getTimeHasBooked(Long timeFrameId, int year);

    void deactiveResortNotifyBookingUser(Long resortId);

    void deactivePropertyNotifyBookingUser(Long propertyId);

    String returnPointBooking(Long bookingId) throws InterruptedException;

    HistoryBookingDetailResponse historyBookingByUUID(String uuid);

}
