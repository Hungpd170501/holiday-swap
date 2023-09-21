package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;

public interface ILoggingService {
    public void saveLog(long fromID, long toID, Long amount, EnumPaymentStatus.BankCodeError resultCode, String detail, int fromBalance, int toBalance);
}
