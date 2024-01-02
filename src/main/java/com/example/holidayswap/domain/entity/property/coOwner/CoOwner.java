package com.example.holidayswap.domain.entity.property.coOwner;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.property.Property;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTime;
import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrame;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "co_owner")
public class CoOwner {
    @Id
    @Column(name = "co_owner_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_time", columnDefinition = "date")
    private LocalDate startTime;

    @Column(name = "end_time", columnDefinition = "date")
    private LocalDate endTime;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @NotNull
    private ContractType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private CoOwnerStatus status;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted = false;

    @Column(name = "property_id")
    @NotNull
    private Long propertyId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id",
            referencedColumnName = "property_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private Property property;

    @Column(name = "user_id")
    @NotNull
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private User user;

    @Size(max = 255)
    @NotNull
    @Column(name = "room_id", nullable = false)
    private String roomId;

    @Column(name = "create_date")
    private Date createDate;

    @OneToMany(mappedBy = "coOwner")
    private List<ContractImage> contractImages;

    @OneToMany(mappedBy = "coOwner")
    private List<TimeFrame> timeFrames;

    @OneToMany(mappedBy = "coOwner")
    private List<AvailableTime> availableTimes;
}