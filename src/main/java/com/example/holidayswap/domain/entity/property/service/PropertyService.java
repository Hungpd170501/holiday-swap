package com.example.holidayswap.domain.entity.property.service;

import com.example.holidayswap.domain.entity.property.Property;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "property_service", schema = "public")
public class PropertyService {
    @EmbeddedId
    private PropertyServiceId id;

    @MapsId("propertyId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    @JsonIgnore
    private Property property;

    @MapsId("serviceTypeId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "service_type_id", nullable = false)
    private ServiceType serviceType;

}