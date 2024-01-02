package com.example.holidayswap.domain.entity.resort;

import com.example.holidayswap.domain.entity.address.District;
import com.example.holidayswap.domain.entity.property.Property;
import com.example.holidayswap.domain.entity.property.PropertyType;
import com.example.holidayswap.domain.entity.resort.amentity.ResortAmenity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "resort")
public class Resort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resort_id", nullable = false)
    private Long id;
    @Size(max = 255)
    @NotNull
    @Length(min = 5, message = "Name length must greater than 5")
    @Column(name = "resort_name")
    private String resortName;
    @Column(name = "resort_description")
    private String resortDescription;
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;
    @Column(name = "resort_status")
    @Enumerated(EnumType.STRING)
    private ResortStatus status;
    @OneToMany(mappedBy = "resort")
    private List<ResortImage> resortImages;
    @ManyToMany
    @JoinTable(
            name = "resorts_amenities",
            joinColumns = @JoinColumn(name = "resort_id"),
            inverseJoinColumns = @JoinColumn(name = "resort_amenity_id"))
    private List<ResortAmenity> amenities;
    @ManyToMany
    @JoinTable(
            name = "resorts_property_type_property",
            joinColumns = @JoinColumn(name = "resort_id"),
            inverseJoinColumns = @JoinColumn(name = "property_type_id"))
    private List<PropertyType> propertyTypes;
    @OneToMany(mappedBy = "resort")
    private List<Property> properties;

    @Column(length = 450, name = "address_line")
    private String addressLine;
    @Column(name = "location_formatted_name")
    private String locationFormattedName;
    @Column(name = "location_description")
    private String locationDescription;
    @Column(name = "location_code", length = 35)
    private String locationCode;
    @Column(name = "postal_code", length = 35)
    private String postalCode;
    @Column
    private Float latitude;
    @Column
    private Float longitude;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "district")
    private District district;

//    @Column(name = "close_date")
//    private LocalDate closeDate;
//
//    @Column(name = "open_date")
//    private LocalDate openDate;

    @OneToMany(mappedBy="resort")
    private Set<ResortMaintance> resortMaintainces;
}