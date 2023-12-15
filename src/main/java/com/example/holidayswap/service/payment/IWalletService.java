package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.entity.payment.Wallet;

public interface IWalletService {
    boolean TopUpWallet(Long userId, Double amount);

    Wallet CreateWallet(Long userId);

    Wallet GetWalletByUserId(Long userId);
}
