package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.request.payment.TopUpWalletDTO;
import com.example.holidayswap.domain.entity.payment.MoneyTranfer;
import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;

import java.util.List;


public interface IMoneyTranferService {
    MoneyTranfer CreateMoneyTranferTransaction(TopUpWalletDTO topUpWalletDTO, EnumPaymentStatus.StatusMoneyTranfer status);

    MoneyTranfer GetMoneyTranferTransaction(Long id);
    void Save(MoneyTranfer moneyTranfer);

    boolean UpdateStatusMoneyTranferTransaction(Long id, EnumPaymentStatus.StatusMoneyTranfer status);
    List<MoneyTranfer> GetMoneyTranferTransactionByUserId(Long id);

}
