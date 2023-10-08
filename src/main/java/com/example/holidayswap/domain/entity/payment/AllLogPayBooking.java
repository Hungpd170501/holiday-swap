package com.example.holidayswap.domain.entity.payment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "all_pay_booking_transaction_log")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllLogPayBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long fromMemberId;
    private long toBookingId;
    private Double amount;
    private EnumPaymentStatus.BankCodeError resultCode;
    private String detail;
    private String createdOn;
    private Double fromBalance;

    private EnumPaymentStatus.TransactionStatus type = EnumPaymentStatus.TransactionStatus.SEND;

}
