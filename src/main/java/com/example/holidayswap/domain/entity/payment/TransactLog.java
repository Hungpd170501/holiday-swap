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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="wallet_from", nullable=false)
    private Wallet walletFrom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="wallet_to", nullable=false)
    private Wallet walletTo;

    @Column(name = "from_total_point")
    private Double fromTotalPoint;

    @Column(name = "to_total_point")
    private Double toTotalPoint;


    private long amountPoint;
    private String createdOn;
}
