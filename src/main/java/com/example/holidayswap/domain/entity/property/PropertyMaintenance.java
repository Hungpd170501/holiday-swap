package com.example.holidayswap.domain.entity.property;

import com.example.holidayswap.domain.entity.resort.Resort;
import com.example.holidayswap.domain.entity.resort.ResortMaintanceImage;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "property_maintaince")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyMaintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maintaince_id")
    private Long id;
    @Column(name = "property_id", nullable = false)
    private Long propertyId;
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ResortStatus type;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "property_id", referencedColumnName = "property_id", nullable = false,
            insertable = false,
            updatable = false)
    private Property property;
    @OneToMany(mappedBy = "propertyMaintenance", cascade = CascadeType.ALL)
    private List<PropertyMaintenanceImage> propertyMaintenanceImages;
}
