package com.example.holidayswap.domain.entity.payment;

import com.example.holidayswap.domain.entity.auth.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToOne(mappedBy = "wallet")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "wallet")
    private Set<TransactionTopUpWallet> transactionTopUpWallets;

    @Column(name = "total_point")
    private Double totalPoint;

    @Column(name = "status")
    private boolean status;

    @JsonIgnore
    @OneToMany(mappedBy = "walletFrom")
    private Set<TransactLog> transactionsFrom;

    @JsonIgnore
    @OneToMany(mappedBy = "walletTo")
    private Set<TransactLog> transactionsTo;

    public boolean withdraw(double amount) {

        boolean check = false;

        if (totalPoint >= amount) {
            totalPoint -= amount;
            check = true;
        }
        return check;
    }

}