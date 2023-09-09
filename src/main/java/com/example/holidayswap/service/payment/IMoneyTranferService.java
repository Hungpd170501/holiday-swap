package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.request.payment.TopUpWalletDTO;
import com.example.holidayswap.domain.entity.payment.MoneyTranfer;

import java.time.LocalDateTime;


public interface IMoneyTranferService {
    MoneyTranfer CreateMoneyTranferTransaction(TopUpWalletDTO topUpWalletDTO, boolean status);
}
