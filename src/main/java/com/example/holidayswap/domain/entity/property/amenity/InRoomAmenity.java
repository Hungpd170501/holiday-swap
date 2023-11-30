package com.example.holidayswap.domain.entity.property.amenity;

import com.example.holidayswap.domain.entity.property.Property;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "in_room_amenity", schema = "public")
public class InRoomAmenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "in_room_amenity_id", nullable = false)
    private Long id;
    @NotNull(message = "In-room amenity name must not null.")
    @Length(min = 5, message = "Name length must greater than 5")
    @Column(name = "in_room_amenity_name", length = Integer.MAX_VALUE)
    private String inRoomAmenityName;
    @Column(name = "in_room_amenity_description", length = Integer.MAX_VALUE)
    private String inRoomAmenityDescription;
    @NotNull
    @Column(name = "in_room_amenity_link_icon", length = Integer.MAX_VALUE)
    private String inRoomAmenityLinkIcon;
    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    @NotNull
    private Boolean isDeleted = false;
    @Column(name = "in_room_amenity_type_id")
    private Long inRoomAmenityTypeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "in_room_amenity_type_id",
            referencedColumnName = "in_room_amenity_type_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private InRoomAmenityType inRoomAmenityType;
    @ManyToMany
    @JoinTable(
            name = "properties_amenities",
            joinColumns = @JoinColumn(name = "in_room_amenity_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id"))
    private List<Property> properties;
}