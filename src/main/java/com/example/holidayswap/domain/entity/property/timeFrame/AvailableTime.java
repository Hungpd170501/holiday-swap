package com.example.holidayswap.domain.entity.property.timeFrame;

import com.example.holidayswap.domain.entity.booking.Booking;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
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
    @Column(name = "start_time")
    private Date startTime;
    @NotNull
    @Column(name = "end_time")
    private Date endTime;
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
    @Column(name = "time_frame_id")
    private Long timeFrameId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "time_frame_id",
            referencedColumnName = "time_frame_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private TimeFrame timeFrame;

    @OneToMany(mappedBy = "availableTime")
    private List<Booking> bookings;
}
