package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;

public interface ITransactionBookingRefundOwnerService {
    public void saveLog(long fromBookingId, long toMemberId, Double amount, EnumPaymentStatus.BankCodeError resultCode, String detail, String createdOn, Double memberBalance);

}
