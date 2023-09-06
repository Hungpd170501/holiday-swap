package com.example.holidayswap.domain.entity.property;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class PropertyImageId implements Serializable {
    private static final long serialVersionUID = 3380730737817289250L;
    @NotNull
    @Column(name = "property_id", nullable = false)
    private Long propertyId;

    @NotNull
    @Column(name = "property_image_id", nullable = false)
    private Long propertyImageId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PropertyImageId entity = (PropertyImageId) o;
        return Objects.equals(this.propertyImageId, entity.propertyImageId) &&
                Objects.equals(this.propertyId, entity.propertyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyImageId, propertyId);
    }

}