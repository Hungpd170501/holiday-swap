package com.example.holidayswap.service.booking;


import com.example.holidayswap.domain.dto.request.booking.BookingRequest;
import com.example.holidayswap.domain.dto.response.booking.*;
import com.example.holidayswap.domain.dto.response.exchange.ExchangeResponse;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import com.example.holidayswap.domain.entity.exchange.Exchange;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerMaintenanceStatus;
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

    void deactivePropertyNotifyBookingUser(Long property, LocalDateTime startDate, LocalDateTime endDate, PropertyStatus resortStatus, List<String> listImage) throws IOException, MessagingException;

    void deactiveApartmentNotifyBookingUser(Long property, String apartmentId, LocalDateTime startDate, LocalDateTime endDate, CoOwnerMaintenanceStatus resortStatus, List<String> listImage) throws IOException, MessagingException;

    String returnPointBooking(Long bookingId) throws InterruptedException;

    void refundPointBookingToOwner(LocalDate date);
    HistoryBookingDetailResponse historyBookingByUUID(String uuid);

    ExchangeResponse createExchange(Exchange exchange) throws InterruptedException, IOException, WriterException;
    void updateExchange(Exchange exchange, EnumBookingStatus.BookingStatus bookingStatus);

    List<BookingCoOwnerResponse> historyBookingByCoOwnerId(Long coOwnerId);
}
