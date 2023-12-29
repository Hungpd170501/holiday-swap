package com.example.holidayswap.service.booking;


import com.example.holidayswap.domain.dto.request.booking.BookingRequest;
import com.example.holidayswap.domain.dto.response.booking.HistoryBookingDetailResponse;
import com.example.holidayswap.domain.dto.response.booking.HistoryBookingResponse;
import com.example.holidayswap.domain.dto.response.booking.HistoryDetailBookingOwnerResponse;
import com.example.holidayswap.domain.dto.response.booking.TimeHasBooked;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IBookingService {
    EnumBookingStatus.BookingStatus createBooking(BookingRequest bookingRequest) throws InterruptedException, IOException, WriterException, MessagingException;

    List<HistoryBookingResponse> historyBookingUserLogin();

    HistoryBookingDetailResponse historyBookingDetail(Long bookingId);

    List<HistoryBookingResponse> historyBookingOwnerLogin();

    HistoryDetailBookingOwnerResponse historyBookingDetailOwner(Long bookingId);

    List<TimeHasBooked> getTimeHasBooked(Long timeFrameId, int year);

    List<TimeHasBooked> getTimeHasBookedByCoOwnerId(Long coOwnerId);

    void deactiveResortNotifyBookingUser(Long resortId, LocalDateTime startDate, LocalDateTime endDate, ResortStatus resortStatus,List<String> listImage) throws IOException, MessagingException;

    void deactivePropertyNotifyBookingUser(Long property, LocalDateTime startDate, LocalDateTime endDate, ResortStatus resortStatus,List<String> listImage) throws IOException, MessagingException;

    String returnPointBooking(Long bookingId) throws InterruptedException;

    void refundPointBookingToOwner(LocalDate date);
    HistoryBookingDetailResponse historyBookingByUUID(String uuid);
}
