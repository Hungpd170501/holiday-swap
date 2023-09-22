package com.example.holidayswap.domain.entity.property.vacation;

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
@Table(name = "time_off_deposit")
public class TimeOffDeposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_off_deposit_id", nullable = false)
    private Long id;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "price_per_night")
    private double pricePerNight;
    @Column(name = "number_night")
    private double numberNight;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted = false;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TimeOffDepositStatus status;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TimeOffDepositType type;

    @Column(name = "vacation_id")
    private Long vacationId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "vacation_id",
            referencedColumnName = "vacation_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private Vacation vacation;
}
