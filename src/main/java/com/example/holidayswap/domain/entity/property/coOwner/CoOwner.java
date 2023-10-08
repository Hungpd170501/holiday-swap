package com.example.holidayswap.domain.entity.property.coOwner;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.property.Property;
import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrame;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "co-owner")
public class CoOwner {
    @EmbeddedId
    private CoOwnerId id;
    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

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

    @JsonIgnore
    @OneToMany(mappedBy = "coOwner", fetch = FetchType.LAZY)
    private Collection<ContractImage> contractImages;

    @OneToMany(mappedBy = "coOwner", fetch = FetchType.LAZY)
    private Collection<TimeFrame> timeFrames;

    @MapsId("propertyId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}