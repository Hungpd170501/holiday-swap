package com.example.holidayswap.domain.entity.property.facility;

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
public class PropertyFacilityId implements Serializable {
    private static final long serialVersionUID = 5082501454452240169L;
    @NotNull
    @Column(name = "property_id", nullable = false)
    private Long propertyId;

    @NotNull
    @Column(name = "facility_id", nullable = false)
    private Long facilityId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PropertyFacilityId entity = (PropertyFacilityId) o;
        return Objects.equals(this.facilityId, entity.facilityId) &&
                Objects.equals(this.propertyId, entity.propertyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(facilityId, propertyId);
    }

}