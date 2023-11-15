package com.example.holidayswap.domain.entity.property;


import com.example.holidayswap.domain.entity.property.amenity.InRoomAmenity;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwner;
import com.example.holidayswap.domain.entity.resort.Resort;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @Column(name = "property_name")
    @NotNull
    private String propertyName;
    @Column(name = "property_description")
    private String propertyDescription;
    @Column(name = "number_king_beds")
//    @Positive
    private int numberKingBeds;
    @Column(name = "number_queen_beds")
//    @Positive
    private int numberQueenBeds;
    @Column(name = "number_single_beds")
//    @Positive
    private int numberSingleBeds;
    @Column(name = "number_double_beds")
//    @Positive
    private int numberDoubleBeds;
    @Column(name = "number_twin_beds")
//    @Positive
    private int numberTwinBeds;
    @Column(name = "number_full_beds")
//    @Positive
    private int numberFullBeds;
    @Column(name = "number_sofa_beds")
//    @Positive
    private int numberSofaBeds;
    @Column(name = "number_murphyBeds")
//    @Positive
    private int numberMurphyBeds;
    @Column(name = "number_beds_room")
    @Positive
    @NotNull
    private int numberBedsRoom;
    @Column(name = "number_baths_room")
    @Positive
    @NotNull
    private int numberBathRoom;
    @Min(value = 10L, message = "The value must be positive, and greater than 10")
    @Positive
    @Column(name = "room_size")
    private double roomSize;
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    @NotNull
    private Boolean isDeleted = false;
    @Column(name = "status", length = Integer.MAX_VALUE)
    @Enumerated(EnumType.STRING)
    @NotNull
    private PropertyStatus status;
    @Column(name = "property_type_id")
    @NotNull
    private Long propertyTypeId;
    @ManyToOne
    @JoinColumn(name = "property_type_id",
            referencedColumnName = "property_type_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private PropertyType propertyType;
    @Column(name = "resort_id")
    @NotNull
    private Long resortId;
    @ManyToOne
    @JoinColumn(name = "resort_id",
            referencedColumnName = "resort_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private Resort resort;
    @Column(name = "property_view_id")
    @NotNull
    private Long propertyViewId;
    @ManyToOne
    @JoinColumn(name = "property_view_id",
            referencedColumnName = "property_view_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private PropertyView propertyView;

    @ManyToMany
    @JoinTable(
            name = "properties_amenities",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "in_room_amenity_id"))
    private List<InRoomAmenity> inRoomAmenities;
//    @OneToMany(mappedBy = "property")
//    private List<Booking> bookingList;
    @OneToMany(mappedBy = "property")
    private List<PropertyImage> propertyImages;
    @OneToMany(mappedBy = "property")
    private List<CoOwner> coOwners;

}