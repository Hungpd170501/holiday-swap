package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;
import com.example.holidayswap.domain.entity.payment.TransactionBookingRefundOwner;

import java.util.List;

public interface ITransactionBookingRefundOwnerService {
    public void saveLog(long fromBookingId, long toMemberId, Double amount, EnumPaymentStatus.BankCodeError resultCode, String detail, String createdOn, Double memberBalance);

    List<TransactionBookingRefundOwner> getTransactionBookingRefundOwnerByUserId(long id);

}
