package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.request.payment.TopUpWalletDTO;

public interface ITransactionService {
     boolean TransactionTopUpWallet(TopUpWalletDTO topUpWalletDTO, boolean status);
}
