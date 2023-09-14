package com.example.holidayswap.domain.entity.property.facility;

import com.example.holidayswap.domain.entity.property.Property;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "property_facility", schema = "public")
public class PropertyFacility {
    @EmbeddedId
    private PropertyFacilityId id;

    @MapsId("facilityTypeId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "facility_type_id", nullable = false)
    private FacilityType facilityType;

    @MapsId("propertyId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

}