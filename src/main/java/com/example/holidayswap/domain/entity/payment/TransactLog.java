package com.example.holidayswap.domain.entity.payment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "transaction_log")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "transact_log_id"
    )
    private Long id;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="wallet_id", nullable=false)
//    private Wallet walletFrom;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="wallet_id", nullable=false)
//    private Wallet walletTo;

    private long amountPoint;
    private String createdOn;
}
