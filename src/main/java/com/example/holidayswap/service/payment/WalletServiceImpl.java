package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.payment.Wallet;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.repository.payment.WalletRepository;
import com.example.holidayswap.service.BankException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class WalletServiceImpl implements IWalletService{

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private UserRepository userRepository;

    public static Map<Long, ReentrantLock> walletLocks = new ConcurrentHashMap<>();

    @PostConstruct
    public void initialize() {
        GetAllWallet();
    }

    @Override
    @Transactional
    public boolean TopUpWallet(Long userId, int amount) {
        Wallet userWallet = walletRepository.findByUser(userRepository.findById(userId).orElse(null));
        if(userWallet == null) userWallet = CreateWallet(userId);
        if(userWallet == null) throw new BankException("Wallet not found");

        userWallet.setTotalPoint(userWallet.getTotalPoint() + amount);
        walletRepository.save(userWallet);
        return true;
    }

    @Override
    public Wallet CreateWallet(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) throw new BankException("User not found");
        Wallet userWallet = user.getWallet();

        if(userWallet == null){
            Wallet wallet = new Wallet();
            wallet.setUser(user);
            wallet.setTotalPoint(0);
            wallet.setStatus(true);
            walletRepository.save(wallet);
            user.setWallet(wallet);
            walletLocks.put(wallet.getId(), new ReentrantLock());
            return wallet;
        }
        return userWallet;
    }

    @Override
    public Wallet GetWalletByUserId(Long userId) {
        Wallet wallet= walletRepository.findByUser(userRepository.findById(userId).orElse(null));
        return wallet;
    }

    @Override
    public Map<Long, ReentrantLock> GetAllWallet() {
         if(walletLocks.isEmpty()){
                List<Wallet> wallets = walletRepository.findAll();
                for(Wallet wallet : wallets){
                    walletLocks.put(wallet.getId(), new ReentrantLock());
                }
         }
         return walletLocks;
    }
}
