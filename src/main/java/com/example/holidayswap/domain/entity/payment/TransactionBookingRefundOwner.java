package com.example.holidayswap.domain.entity.payment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "all_booking_refund_owner_transaction_log")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionBookingRefundOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long fromBookingId;
    private long toMemberId;
    private Double amount;
    private EnumPaymentStatus.BankCodeError resultCode;
    private String detail;
    private String createdOn;
    private Double memberBalance;
    private Double commission;
}
