package com.example.holidayswap.domain.entity.property.timeFrame;

import com.example.holidayswap.domain.entity.property.coOwner.CoOwner;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "time_frame")
public class TimeFrame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_frame_id", nullable = false)
    private Long id;
    @Column(name = "week_number")
    @NotNull
    @Min(value = 1)
    @Max(value = 52)
    private int weekNumber;
    @Column(name = "co_owner_id")
    @NotNull
    private Long coOwnerId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "co_owner_id",
            referencedColumnName = "co_owner_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private CoOwner coOwner;
}
