package com.example.holidayswap.domain.entity.resort;

import com.example.holidayswap.domain.entity.address.Location;
import com.example.holidayswap.domain.entity.resort.ResortAmenity.ResortAmenity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    @Column(name = "resort_name")
    private String resortName;

    @Column(name = "location_id")
    private Long locationId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "location_id",
            referencedColumnName = "location_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private Location location;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

//    @OneToMany(mappedBy = "resort")
//    private List<Property> properties = new LinkedHashSet<>();

    @OneToMany(mappedBy = "resort")
    private List<ResortImage> resortImages;

    @ManyToMany
    private List<ResortAmenity> amenities;

}