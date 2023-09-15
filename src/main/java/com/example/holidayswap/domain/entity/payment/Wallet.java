package com.example.holidayswap.domain.entity.payment;

import com.example.holidayswap.domain.entity.auth.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "wallet")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
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

//    @OneToMany(mappedBy = "walletFrom")
//    private Set<TransactLog> transactionsFrom;
//
//    @OneToMany(mappedBy = "walletTo")
//    private Set<TransactLog> transactionsTo;


}
