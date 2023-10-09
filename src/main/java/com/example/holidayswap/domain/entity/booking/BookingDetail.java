package com.example.holidayswap.domain.entity.booking;

import com.example.holidayswap.domain.entity.property.ownership.Ownership;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "booking_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_detail_id")
    private Long id;

    @Column(name = "book_id", nullable = false)
    private Long bookId;
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "book_id",nullable = false,
            insertable = false,
            updatable = false)
    private Booking booking;

    @Column(name = "property_id", nullable = false)
    private Long propertyId;

    @Column(name = "room_id", nullable = false)
    private String roomId;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "room_id",
            referencedColumnName = "room_id",
            nullable = false,
            insertable = false,
            updatable = false)
    @JoinColumn(name = "property_id",
            referencedColumnName = "property_id",
            nullable = false,
            insertable = false,
            updatable = false)
    @JoinColumn(name = "user_id",
            referencedColumnName = "user_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private Ownership ownership;

    @Column(name = "check_in_date", nullable = false)
    private Date checkInDate;

    @Column(name = "check_out_date", nullable = false)
    private Date checkOutDate;

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    @Column(name = "total_point", nullable = false)
    private Double totalPoint;

}
