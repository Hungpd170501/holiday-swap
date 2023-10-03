package com.example.holidayswap.domain.entity.property.ownership;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.property.Property;
import com.example.holidayswap.domain.entity.property.vacation.VacationUnit;
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
@Table(name = "ownership")
public class Ownership {
    @EmbeddedId
    private OwnershipId id;
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
    private ContractStatus status;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted = false;

    @JsonIgnore
    @OneToMany(mappedBy = "ownership", fetch = FetchType.LAZY)
    private Collection<ContractImage> contractImages;

    @OneToMany(mappedBy = "ownership", fetch = FetchType.LAZY)
    private Collection<VacationUnit> vacations;

    @MapsId("propertyId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}