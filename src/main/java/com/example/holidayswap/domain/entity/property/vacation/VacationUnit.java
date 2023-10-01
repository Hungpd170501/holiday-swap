package com.example.holidayswap.domain.entity.property.vacation;

import com.example.holidayswap.domain.entity.property.ownership.Ownership;
import jakarta.persistence.*;
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
@Table(name = "vacation_unit")
public class VacationUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_unit_id", nullable = false)
    private Long id;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted = false;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private VacationStatus status;
    @Column(name = "property_id")
    private Long propertyId;
    @Column(name = "user_id")
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
    private Ownership ownership;
    @OneToMany(mappedBy = "vacation")
    private List<TimeOffDeposit> timeOffDeposits;

}
