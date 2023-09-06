package com.example.holidayswap.domain.entity.property;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "property_images", schema = "public")
public class PropertyImage {
    @EmbeddedId
    private PropertyImageId id;

    @MapsId("propertyId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @MapsId("propertyImageId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_image_id", nullable = false)
    private ImageProperty propertyImage;

}