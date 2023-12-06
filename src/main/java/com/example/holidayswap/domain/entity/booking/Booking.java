package com.example.holidayswap.domain.entity.booking;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.property.rating.Rating;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "booking")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "available_time_id", nullable = false)
    private Long availableTimeId;
    @ManyToOne
    @JoinColumn(name = "available_time_id", referencedColumnName = "available_time_id", nullable = false,
            insertable = false,
            updatable = false)
    private AvailableTime availableTime;


    @Column(name = "user_booking_id", nullable = false)
    private Long userBookingId;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "total_days", nullable = false)
    private Long totalDays;

    @ManyToOne
    @JoinColumn(name = "user_booking_id", referencedColumnName = "user_id", nullable = false,
            insertable = false,
            updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    private User userOwner;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "check_in_date", nullable = false)
    private Date checkInDate;

    @Column(name = "check_out_date", nullable = false)
    private Date checkOutDate;

    @Column(name = "commission", nullable = false)
    private Double commission;

    @Column(name = "actual_price", nullable = false)
    private Double actualPrice;

    @Column(name = "date_booking", nullable = false)
    private String dateBooking;


    @Column(name = "totalMember", nullable = false)
    private int totalMember;

    @Column(name = "status_check_return", nullable = false)
    private Boolean statusCheckReturn;

    @OneToMany(mappedBy = "booking")
    private Set<UserOfBooking> userOfBookings;

    @Column(name = "status", nullable = false)
    private EnumBookingStatus.BookingStatus status;
    @OneToOne(mappedBy = "booking")
    private Rating rating;

}
