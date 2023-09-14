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
    private static final long serialVersionUID = 6480270893354400353L;
    @NotNull
    @Column(name = "facility_type_id", nullable = false)
    private Long facilityTypeId;

    @NotNull
    @Column(name = "property_id", nullable = false)
    private Long propertyId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PropertyFacilityId entity = (PropertyFacilityId) o;
        return Objects.equals(this.facilityTypeId, entity.facilityTypeId) &&
                Objects.equals(this.propertyId, entity.propertyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(facilityTypeId, propertyId);
    }

}