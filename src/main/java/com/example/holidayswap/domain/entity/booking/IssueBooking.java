package com.example.holidayswap.domain.entity.booking;

import com.example.holidayswap.domain.entity.common.BaseEntityAudit;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "issue_booking")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IssueBooking extends BaseEntityAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id")
    private Long id;
    @Column(name = "book_id", nullable = false)
    private Long bookingId;
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "book_id", nullable = false,
            insertable = false,
            updatable = false)
    private Booking booking;

    @Column(name = "status", nullable = false)
    private EnumBookingStatus.IssueBookingStatus status;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "note")
    private String note;

}
