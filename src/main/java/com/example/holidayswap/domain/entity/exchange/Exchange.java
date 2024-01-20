package com.example.holidayswap.domain.entity.exchange;

import com.example.holidayswap.domain.entity.common.BaseEntityAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "exchange")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Exchange extends BaseEntityAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_id")
    private Long exchangeId;

    @Column(name = "request_user_id", nullable = false)
    private Long requestUserId;

    @Column(name = "request_available_time_id", nullable = false)
    private Long requestAvailableTimeId;

    @Column(name = "request_check_in_date", nullable = false)
    private LocalDate requestCheckInDate;

    @Column(name = "request_check_out_date", nullable = false)
    private LocalDate requestCheckOutDate;

    @Column(name = "request_total_member", nullable = false)
    private int requestTotalMember;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status", nullable = false)
    private ExchangeStatus requestStatus;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "available_time_id", nullable = false)
    private Long availableTimeId;

    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    @Column(name = "total_member")
    private int totalMember;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ExchangeStatus status;

    @Column(name = "request_booking_id")
    private Long requestBookingId;

    @Column(name = "booking_id")
    private Long bookingId;

    @Enumerated(EnumType.STRING)
    @Column(name = "overall_status", nullable = false)
    private ExchangeStatus overallStatus;
}
