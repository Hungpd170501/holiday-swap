package com.example.holidayswap.domain.entity.exchange;

import com.example.holidayswap.domain.entity.booking.UserOfBooking;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "exchange")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_id")
    private Long exchangeId;

    @Column(name = "available_time_id_of_user1", nullable = false)
    private Long availableTimeIdOfUser1;

    @Column(name = "total_member_of_user1", nullable = false)
    private int totalMemberOfUser1;

    @Column(name = "check_in_date_of_user1", nullable = false)
    private Date checkInDateOfUser1;

    @Column(name = "check_out_date_of_user1", nullable = false)
    private Date checkOutDateOfUser1;
    @Column(name = "price_of_user1", nullable = false)
    private Double priceOfUser1;
    @Column(name = "available_time_id_of_user2", nullable = false)
    private Long availableTimeIdOfUser2;

    @Column(name = "total_member_of_user2", nullable = false)
    private int totalMemberOfUser2;

    @Column(name = "check_in_date_of_user2", nullable = false)
    private Date checkInDateOfUser2;

    @Column(name = "check_out_date_of_user2", nullable = false)
    private Date checkOutDateOfUser2;
    @Column(name = "price_of_user2", nullable = false)
    private Double priceOfUser2;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "booking_id_of_user1")
    private Long bookingIdOfUser1;
    @Column(name = "booking_id_of_user2")
    private Long bookingIdOfUser2;
    @Column(name = "user_id_of_user1")
    private Long userIdOfUser1;
    @Column(name = "user_id_of_user2")
    private Long userIdOfUser2;

    @Column(name = "status_user1")
    private String statusUser1;
    @Column(name = "status_user1")
    private String statusUser2;


}
