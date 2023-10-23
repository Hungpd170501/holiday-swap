package com.example.holidayswap.domain.entity.booking;

import com.example.holidayswap.domain.entity.auth.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_of_booking")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserOfBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fullName;
    @Column
    private String email;
    @Column
    private String phoneNumber;

    @Column(name = "book_id", nullable = false)
    private Long bookingId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "book_id", nullable = false,
            insertable = false,
            updatable = false)
    private Booking booking;
}
