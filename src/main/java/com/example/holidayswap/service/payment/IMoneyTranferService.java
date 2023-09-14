package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.request.payment.TopUpWalletDTO;
import com.example.holidayswap.domain.entity.payment.MoneyTranfer;
import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;


public interface IMoneyTranferService {
    MoneyTranfer CreateMoneyTranferTransaction(TopUpWalletDTO topUpWalletDTO, EnumPaymentStatus.StatusMoneyTranfer status);

    MoneyTranfer GetMoneyTranferTransaction(Long id);

    boolean UpdateStatusMoneyTranferTransaction(Long id, EnumPaymentStatus.StatusMoneyTranfer status);
}
