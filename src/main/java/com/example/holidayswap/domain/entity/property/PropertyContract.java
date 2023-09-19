package com.example.holidayswap.domain.entity.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "property_contract")
public class PropertyContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_contract_id", nullable = false)
    private Long id;

    @Column(name = "end_period")
    private OffsetDateTime endPeriod;

    @Column(name = "end_time")
    private OffsetDateTime endTime;

    @Column(name = "start_time")
    private OffsetDateTime startTime;
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted = false;

    @Column(name = "property_id")
    private Long propertyId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "property_id",
            referencedColumnName = "property_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private Property property;
    @OneToMany(mappedBy = "propertyContract")
//    @Fetch(FetchMode.SUBSELECT)
    private List<ContractImage> contractImages;
}