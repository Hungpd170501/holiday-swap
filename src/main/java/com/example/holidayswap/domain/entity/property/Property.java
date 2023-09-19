package com.example.holidayswap.domain.entity.property;


import com.example.holidayswap.domain.entity.auth.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @Column(name = "number_king_beds")
    private int numberKingBeds;
    @Column(name = "number_qeen_beds")
    private int numerQeensBeds;
    @Column(name = "number_twin_beds")
    private int numberTwinBeds;
    @Column(name = "number_full_beds")
    private int numberFullBeds;
    @Column(name = "number_sofa_beds")
    private int numberSofaBeds;
    @Column(name = "number_murphyBeds")
    private int numberMurphyBeds;
    @Column(name = "number_beds_room")
    private int numberBedsRoom;
    @Column(name = "number_baths_room")
    private int numberBathRoom;
    @Column(name = "room_size")
    private double roomSize;

    @Column(name = "room_view_id")
    private int roomViewId;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private Boolean isDeleted = false;

    @Column(name = "status", length = Integer.MAX_VALUE)
    @Enumerated(EnumType.STRING)
    private PropertyStatus status;

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
    private List<PropertyContract> propertyContracts;
    
    @OneToMany(mappedBy = "property")
    private List<PropertyImage> propertyImages;

//    @ManyToMany
//    @JoinTable(name = "property_facility",
//            joinColumns = @JoinColumn(name = "property_id"),
//            inverseJoinColumns = @JoinColumn(name = "facility_id"))
//    private List<Facility> facilities = new LinkedHashSet<>();
//
//    @ManyToMany
//    @JoinTable(name = "property_service",
//            joinColumns = @JoinColumn(name = "property_id"),
//            inverseJoinColumns = @JoinColumn(name = "service_id"))
//    private List<Service> services = new LinkedHashSet<>();
}