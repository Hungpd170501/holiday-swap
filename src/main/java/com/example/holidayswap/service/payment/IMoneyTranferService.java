package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.request.payment.TopUpWalletDTO;
import com.example.holidayswap.domain.entity.payment.MoneyTranfer;
import com.example.holidayswap.domain.entity.payment.StatusPayment;

import java.time.LocalDateTime;


public interface IMoneyTranferService {
    MoneyTranfer CreateMoneyTranferTransaction(TopUpWalletDTO topUpWalletDTO, StatusPayment status);

    MoneyTranfer GetMoneyTranferTransaction(Long id);

    boolean UpdateStatusMoneyTranferTransaction(Long id, StatusPayment status);
}
