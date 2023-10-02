package com.example.holidayswap.domain.entity.property;


import com.example.holidayswap.domain.entity.property.amenity.InRoomAmenity;
import com.example.holidayswap.domain.entity.property.ownership.Ownership;
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

    @Column(name = "property_name")
    private String propertyName;
    @Column(name = "property_description")
    private String propertyDescription;
    @Column(name = "number_king_beds")
    private int numberKingBeds;
    @Column(name = "number_qeen_beds")
    private int numberQueensBeds;
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
    @Column(name = "property_view_id")
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
    @OneToMany
    private List<Ownership> ownerships;
}