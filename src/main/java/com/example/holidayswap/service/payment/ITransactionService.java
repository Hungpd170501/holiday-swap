package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.request.payment.TopUpWalletDTO;
import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;
import com.example.holidayswap.domain.entity.payment.MoneyTranfer;

public interface ITransactionService {

    boolean TransactionTopUpWallet(TopUpWalletDTO topUpWalletDTO, EnumPaymentStatus.StatusMoneyTranfer status, Long moneyTranferId);
}
