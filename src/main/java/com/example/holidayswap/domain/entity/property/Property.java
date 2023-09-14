package com.example.holidayswap.domain.entity.property;

//import com.example.holidayswap.domain.entity.property.facility.FacilityType;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.property.facility.FacilityType;
import com.example.holidayswap.domain.entity.property.service.ServiceType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "property", schema = "public")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id", nullable = false)
    private Long id;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "status", length = Integer.MAX_VALUE)
    private String status;

    @Column(name = "property_type_id")
    private Long propertyTypeId;
    @ManyToOne
    @JoinColumn(name = "property_type_id",
            referencedColumnName = "property_type_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private PropertyType propertyType;

    @Column(name = "resort_id")
    private Long resortId;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "resort_id",
            referencedColumnName = "resort_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private Resort resort;

    @Column(name = "user_id")
    private Long userId;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id",
            referencedColumnName = "user_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private User user;

    @OneToMany(mappedBy = "property")
    private Set<PropertyContract> propertyContracts= new LinkedHashSet<>();


    @OneToMany(mappedBy = "property")
    private Set<PropertyImage> propertyImages = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "property_facility",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_type_id"))
    private Set<FacilityType> facilityType = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "property_service",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "service_type_id"))
    private Set<ServiceType> serviceType = new LinkedHashSet<>();

}