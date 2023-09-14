package com.example.holidayswap.domain.entity.property.service;

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
public class PropertyServiceId implements Serializable {
    private static final long serialVersionUID = -4972679487960470770L;
    @NotNull
    @Column(name = "property_id", nullable = false)
    private Long propertyId;

    @NotNull
    @Column(name = "service_type_id", nullable = false)
    private Long serviceTypeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PropertyServiceId entity = (PropertyServiceId) o;
        return Objects.equals(this.serviceTypeId, entity.serviceTypeId) &&
                Objects.equals(this.propertyId, entity.propertyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceTypeId, propertyId);
    }

}