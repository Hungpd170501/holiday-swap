package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;

public interface IAllLogPayBookingService {
    public void saveLog(long fromMemberId, long toBookingId, Double amount, EnumPaymentStatus.BankCodeError resultCode, String detail, String createdOn, Double fromBalance);

}
