package com.example.holidayswap.domain.dto.response.payment;

import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponse {
    private EnumPaymentStatus.BankCodeError resultCode; //200 success, 404 account not found, 405 balance not enought
    private String message;
    private String transferDate;
}
