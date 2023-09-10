package com.example.holidayswap.domain.entity.property;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.property.facility.Facility;
import com.example.holidayswap.domain.entity.property.service.Service;
import com.example.holidayswap.domain.entity.resort.Resort;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "property", schema = "public")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resort_id")
    private Resort resort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_type_id")
    private PropertyType propertyType;

    @Column(name = "status", length = Integer.MAX_VALUE)
    @Enumerated(EnumType.STRING)
    private PropertyStatus status;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToMany(mappedBy = "property")
    private Set<PropertyAvailableTime> propertyAvailableTimes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "property")
    private Set<PropertyContract> propertyContracts = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "property_facility",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id"))
    private Set<Facility> facilities = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "property_images",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "property_image_id"))
    private Set<ImageProperty> imageProperties = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "property_service",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<Service> services = new LinkedHashSet<>();

}