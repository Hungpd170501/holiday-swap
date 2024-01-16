package com.example.holidayswap.domain.entity.property.coOwner;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "owner_ship_maintenance")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OwnerShipMaintenance {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(name = "property_id")
    private Long propertyId;
    @Column(name = "apartment_id")
    private String apartmentId;
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CoOwnerMaintenanceStatus type;
    @OneToMany(mappedBy = "ownershipMaintenance", cascade = CascadeType.ALL)
    private List<OwnerShipMaintenanceImage> ownershipMaintenanceImages;
}
