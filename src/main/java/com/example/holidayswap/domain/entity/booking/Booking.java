package com.example.holidayswap.domain.entity.booking;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.payment.MoneyTranfer;
import com.example.holidayswap.domain.entity.property.Property;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
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

    @Column(name = "property_id", nullable = false)
    private Long propertyId;
    @ManyToOne
    @JoinColumn(name = "property_id", referencedColumnName = "property_id", nullable = false,
            insertable = false,
            updatable = false)
    private Property property;

    @Column(name = "room_id", nullable = false)
    private String roomId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false,
            insertable = false,
            updatable = false)
    private User user;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "check_in_date", nullable = false)
    private Date checkInDate;

    @Column(name = "check_out_date", nullable = false)
    private Date checkOutDate;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<BookingDetail> bookingDetail;

    @OneToMany(mappedBy = "booking")
    private Set<UserOfBooking> userOfBookings;

    @Column(name = "status", nullable = false)
    private EnumBookingStatus.BookingStatus status;
}
