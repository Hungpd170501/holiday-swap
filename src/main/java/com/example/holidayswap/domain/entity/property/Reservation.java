package com.example.holidayswap.domain.entity.property;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.property.vacation.TimeOffDeposit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", nullable = false)
    private Long id;

    @Column(name = "user_id")
    private Long userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id",
            referencedColumnName = "user_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private User user;

    @Column(name = "checkin")
    private Date checkin;
    @Column(name = "checkout")
    private Date checkout;
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted = false;
    @Column(name = "status")
    private String status;
    @Column(name = "type")
    private String type;
    @Column(name = "note")
    private String note;
    @Column(name = "price_per_night")
    private double pricePerNight;
    @Column(name = "number_night")
    private double numberNight;

    @Column(name = "total_price")
    private double totalPrice;
    @Column(name = "time_off_deposit_id")
    private Long timeOffDepositId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "time_off_deposit_id",
            referencedColumnName = "time_off_deposit_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private TimeOffDeposit timeOffDeposit;

}
