package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.entity.payment.Wallet;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public interface IWalletService {
    boolean TopUpWallet(Long userId, int amount);

    Wallet CreateWallet(Long userId);
    Wallet GetWalletByUserId(Long userId);

    Map<Long, ReentrantLock> GetAllWallet();
}
