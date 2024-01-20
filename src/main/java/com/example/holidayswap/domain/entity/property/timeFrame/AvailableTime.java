package com.example.holidayswap.domain.entity.property.timeFrame;

import com.example.holidayswap.domain.entity.booking.Booking;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwner;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "available_time")
public class  AvailableTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "available_time_id", nullable = false)
    private Long id;
    @NotNull
    @Column(name = "start_time", columnDefinition = "date")
    private LocalDate startTime;
    @NotNull
    @Column(name = "end_time", columnDefinition = "date")
    private LocalDate endTime;
    @Column(name = "price_per_night")
    @NotNull
    @Positive(message = "Price can not be negative value")
    private double pricePerNight;
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    @NotNull
    private boolean isDeleted = false;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private AvailableTimeStatus status;
    @NotNull
    @Column(name = "co_owner_id")
    private Long coOwnerId;
    @ManyToOne(fetch = FetchType.LAZY á fashfsdgkjhbn)
    @JoinColumn(name = "co_owner_id",
            referencedColumnName = "co_owner_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private CoOwner coOwner;

    @OneToMany(mappedBy = "availableTime")
    private List<Booking> bookings;
}
