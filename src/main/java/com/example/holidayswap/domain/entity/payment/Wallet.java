package com.example.holidayswap.domain.entity.payment;

import com.example.holidayswap.domain.entity.auth.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Entity
@Table(name = "wallet")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    private ReentrantLock lock = new ReentrantLock();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "wallet_id"
    )
    private Long id;

    @OneToOne(mappedBy = "wallet")
    private User user;

    @OneToMany(mappedBy="wallet")
    private Set<TransactionTopUpWallet> transactionTopUpWallets;

    @Column(name = "total_point")
    private int totalPoint;

    @Column(name = "status")
    private boolean status;

    @OneToMany(mappedBy = "walletFrom")
    private Set<TransactLog> transactionsFrom;

    @OneToMany(mappedBy = "walletTo")
    private Set<TransactLog> transactionsTo;

    public synchronized boolean withdraw(double amount) {

        boolean check= false;
        lock.lock();
        try {
            if (totalPoint >= amount) {
                totalPoint -= amount;
                check = true;
            } else {
                System.out.println("Insufficient funds.");
            }
        } finally {
            lock.unlock();
        }
        return check;
    }

}
