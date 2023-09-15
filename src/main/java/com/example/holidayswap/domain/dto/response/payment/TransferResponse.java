package com.example.holidayswap.domain.dto.response.payment;

import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;

public class TransferResponse {
    private EnumPaymentStatus.BankCodeError resultCode; //200 success, 404 account not found, 405 balance not enought
    private String message;
    private String transferDate;
}
