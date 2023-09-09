package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.payment.Wallet;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.repository.payment.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class WalletServiceImpl implements IWalletService{

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public boolean TopUpWallet(Long userId, int amount) {
        Wallet userWallet = walletRepository.findByUser(userRepository.findById(userId).get());
        if(userWallet == null) userWallet = CreateWallet(userId);

        userWallet.setTotalPoint(userWallet.getTotalPoint() + amount);
        walletRepository.save(userWallet);
        return false;
    }

    @Override
    public Wallet CreateWallet(Long userId) {
        User user = userRepository.findById(userId).get();
        Wallet userWallet = walletRepository.findByUser(user);

        if(userWallet == null){
            Wallet wallet = new Wallet();
            wallet.setUser(user);
            wallet.setTotalPoint(0);
            wallet.setStatus(true);
            walletRepository.save(wallet);
        }
        return userWallet;
    }
}
