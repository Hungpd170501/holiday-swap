package com.example.holidayswap.domain.entity.payment;

import com.example.holidayswap.domain.entity.auth.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transaction_top_up_wallet")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionTopUpWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "wallet_transaction_id"
    )
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id", referencedColumnName = "wallet_id")
    private Wallet wallet;

    @Column
    private int amount;
    @Column(name = "payment_date")
    private String paymentDate;
    @Column
    private EnumPaymentStatus.TransactionStatus type = EnumPaymentStatus.TransactionStatus.RECIVED;
}
