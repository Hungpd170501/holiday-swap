package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.request.payment.TopUpWalletDTO;

public interface ITransactionService {
     void TransactionTopUpWallet(TopUpWalletDTO topUpWalletDTO, boolean status);
}
