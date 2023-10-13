package com.example.holidayswap.domain.entity.property.timeFrame;

import com.example.holidayswap.domain.entity.property.coOwner.CoOwner;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    private int weekNumber;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted = false;
    @Column(name = "status")
    @NotNull
    @Enumerated(EnumType.STRING)
    private TimeFrameStatus status;
    @NotNull
    @Column(name = "property_id")
    private Long propertyId;
    @Column(name = "user_id")
    @NotNull
    private Long userId;
    @Column(name = "room_id")
    private String roomId;
    @ManyToOne
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
    @JoinColumn(name = "room_id",
            referencedColumnName = "room_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private CoOwner coOwner;
    @OneToMany(mappedBy = "timeFrame")
    private List<AvailableTime> availableTimes;

}
