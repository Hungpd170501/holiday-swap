package com.example.holidayswap.service.booking;

import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import com.example.holidayswap.domain.entity.booking.IssueBooking;

import java.util.List;

public interface IIssueBookingService {
    void createIssueBooking(Long bookingId, String issue);
    void updateIssueBooking(Long issuedId, String note, EnumBookingStatus.IssueBookingStatus status) throws InterruptedException;
    List<IssueBooking> getAllIssueBooking();
    IssueBooking getIssueBookingById(Long issueId);
}
