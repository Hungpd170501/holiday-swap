package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.entity.payment.AllLogPayBooking;
import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;

import java.util.List;

public interface IAllLogPayBookingService {
    public void saveLog(long fromMemberId, long toBookingId, Double amount, EnumPaymentStatus.BankCodeError resultCode, String detail, String createdOn, Double fromBalance);

    public List<AllLogPayBooking> getAllLogPayBookingByUserId(long userId);
}
