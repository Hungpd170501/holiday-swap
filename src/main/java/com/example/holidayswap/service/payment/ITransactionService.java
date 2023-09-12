package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.request.payment.TopUpWalletDTO;
import com.example.holidayswap.domain.entity.payment.StatusPayment;

public interface ITransactionService {

    boolean TransactionTopUpWallet(TopUpWalletDTO topUpWalletDTO, StatusPayment status, Long moneyTranferId);
}
